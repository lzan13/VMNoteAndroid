package com.vmloft.develop.app.vmnote.common;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.bean.AUser;
import com.vmloft.develop.app.vmnote.common.utils.ARXUtils;
import com.vmloft.develop.library.tools.utils.VMStr;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Create by lzan13 on 2019/05/09 15:48
 *
 * 登录注册管理类
 */
public class ASignManager {

    // 当前登录账户
    private AUser mCurrentUser;
    private AUser mHistoryUser;

    /**
     * 私有构造
     */
    private ASignManager() {
    }

    /**
     * 内部类实现单例模式
     */
    private static class InnerHolder {
        private static final ASignManager INSTANCE = new ASignManager();
    }

    /**
     * 获取的实例
     */
    public static final ASignManager getInstance() {
        return InnerHolder.INSTANCE;
    }

    /**
     * 通过邮箱注册
     *
     * @param email    用户邮箱
     * @param password 用户密码
     * @param callback 回调
     */
    public void signUp(final String email, final String password, final ACallback<AUser> callback) {
        Observable<AUser> observable = Observable.create((final ObservableEmitter<AUser> emitter) -> {
            final AUser user = new AUser();
            user.setUsername(email);
            user.setEmail(email);
            user.setPassword(password);
            // 注册填写默认昵称和签名
            user.setNickname(VMStr.byRes(R.string.nickname_default));
            user.setSignature(VMStr.byRes(R.string.signature_default));
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        setHistoryUser(user);
                        emitter.onNext(user);
                    } else {
                        emitter.onError(e);
                    }
                }
            });
        });
        observable.compose(ARXUtils.<AUser>threadScheduler()).subscribe(new AObserver<AUser>(callback));
    }

    /**
     * 通过邮箱登录
     *
     * @param email    用户邮箱
     * @param password 用户密码
     * @param callback 回调
     */
    public void signIn(final String email, final String password, final ACallback<AUser> callback) {
        Observable<AUser> observable = Observable.create((final ObservableEmitter<AUser> emitter) -> {
            AVUser.logInInBackground(email, password, new LogInCallback<AUser>() {
                @Override
                public void done(AUser user, AVException e) {
                    if (e == null) {
                        setHistoryUser(user);
                        emitter.onNext(user);
                    } else {
                        emitter.onError(e);
                    }
                }
            }, AUser.class);
        });
        observable.compose(ARXUtils.<AUser>threadScheduler()).subscribe(new AObserver<AUser>(callback));
    }

    /**
     * 退出登录
     */
    public void signOut() {
        AVUser.logOut();

        reset();
    }

    /**
     * 判断是否登录
     */
    public boolean isSingIn() {
        if (getCurrentUser() == null) {
            return false;
        }
        return true;
    }

    public AUser getCurrentUser() {
        if (mCurrentUser == null) {
            mCurrentUser = AVUser.getCurrentUser(AUser.class);
        }
        return mCurrentUser;
    }

    /**
     * 获取上次登录的账户信息
     */
    public AUser getHistoryUser() {
        if (mHistoryUser == null) {
            mHistoryUser = parseUser(ASPManager.getInstance().getHistoryUser());
        }
        return mHistoryUser;
    }

    /**
     * 记录上次登录的账户，下次登录可以查询
     */
    public void setHistoryUser(AUser user) {
        mHistoryUser = user;
        ASPManager.getInstance().putHistoryUser(convertUser(user));
    }

    /**
     * 通过 json 文件解析用户信息，这个主要是保存的登录用户信息
     *
     * @param userStr 用户信息 json 串
     */
    public AUser parseUser(String userStr) {
        if (VMStr.isEmpty(userStr)) {
            return null;
        }
        AUser user = new AUser();
        try {
            JSONObject object = new JSONObject(userStr);
            user.setUsername(object.optString("username"));
            user.setEmail(object.optString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 用户信息转为 json 串
     */
    public String convertUser(AUser user) {
        if (user == null) {
            return "";
        }
        JSONObject object = new JSONObject();
        try {
            object.put("username", user.getUsername());
            object.put("email", user.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    /**
     * 重置
     */
    public void reset() {
        mHistoryUser = null;
        mCurrentUser = null;
    }
}
