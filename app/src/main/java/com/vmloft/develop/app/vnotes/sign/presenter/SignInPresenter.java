package com.vmloft.develop.app.vnotes.sign.presenter;

import android.os.Handler;
import android.os.Looper;

import com.vmloft.develop.app.vnotes.network.Account;
import com.vmloft.develop.app.vnotes.network.NetManager;
import com.vmloft.develop.app.vnotes.sign.model.ISign;
import com.vmloft.develop.app.vnotes.sign.model.SignInModel;
import com.vmloft.develop.app.vnotes.sign.view.ISignView;
import com.vmloft.develop.library.tools.VMCallback;
import com.vmloft.develop.library.tools.utils.VMLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lzan13 on 2017/11/23.
 */
public class SignInPresenter implements ISignPresenter {
    private ISign sign;
    private ISignView signView;

    private Handler handler;

    public SignInPresenter(ISignView signView) {
        this.signView = signView;
        init();
    }

    private void init() {
        sign = new SignInModel();
        signView.init(sign.getAccount());
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 处理登录操作
     *
     * @param email 账户
     * @param password 密码
     */
    @Override
    public void doSignIn(String email, String password) {
        sign.saveAccount(email);
        signView.onProgressState(true);
        JSONObject object = new JSONObject();
        try {
            object.put("email", email);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetManager.getInstance().createAccount(object, new VMCallback() {
            @Override
            public void onDone(Object object) {
                VMLog.i("create account success " + object);
                signView.onSignResult(true, 0);
                signView.onProgressState(false);
            }

            @Override
            public void onError(int code, String desc) {
                VMLog.i("create account error code: %d, desc: %s", code, desc);
                signView.onSignResult(false, code);
                signView.onProgressState(false);
            }

            @Override
            public void onProgress(int progress, String desc) {

            }
        });
    }
}
