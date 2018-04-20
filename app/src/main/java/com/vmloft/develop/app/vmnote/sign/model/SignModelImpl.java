package com.vmloft.develop.app.vmnote.sign.model;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.bean.UserBean;
import com.vmloft.develop.app.vmnote.api.ApiManager;
import com.vmloft.develop.app.vmnote.utils.RXUtils;
import com.vmloft.develop.library.tools.utils.VMLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录相关数据处理实现
 */
public class SignModelImpl implements ISignModel {

    /**
     * 创建账户
     */
    @Override
    public void createAccount(UserBean user, final Callback callback) {
        SPManager.getInstance().putAccount(user.getEmail());
        ApiManager.getInstance()
                .accountApi()
                .createAccount(user.getEmail(), user.getPassword())
                .compose(RXUtils.<BaseResult<UserBean>>threadScheduler())
                .subscribe(new Observer<BaseResult<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserBean> result) {
                        if (result.getCode() == 0) {
                            UserBean user = result.getData();
                            callback.onDone(user);
                        } else {
                            callback.onError(result.getCode(), result.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ApiManager.getInstance().parseThrowable(e, callback);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 认证账户
     */
    @Override
    public void authAccount(UserBean user, final Callback callback) {
        SPManager.getInstance().putAccount(user.getEmail());
        ApiManager.getInstance()
                .accountApi()
                .authAccount(user.getEmail(), user.getPassword())
                .compose(RXUtils.<BaseResult<UserBean>>threadScheduler())
                .subscribe(new Observer<BaseResult<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserBean> result) {
                        if (result.getCode() == 0) {
                            UserBean user = result.getData();
                            SPManager.getInstance().putToken(user.getToken());
                            callback.onDone(user);
                        } else {
                            callback.onError(result.getCode(), result.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ApiManager.getInstance().parseThrowable(e, callback);
                    }

                    @Override
                    public void onComplete() {
                        VMLog.d("authAccount complete");
                    }
                });

    }

    @Override
    public String getLastAccount() {
        return SPManager.getInstance().getAccount();
    }
}
