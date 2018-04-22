package com.vmloft.develop.app.vmnote.sign.presenter;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.sign.model.ISignModel;
import com.vmloft.develop.app.vmnote.sign.model.SignModelImpl;
import com.vmloft.develop.app.vmnote.sign.view.ISignInView;


/**
 * Created by lzan13 on 2017/11/23.
 * 登录处理实现
 */
public class SignPresenterImpl implements ISignPresenter {
    private ISignModel signInModel;
    private ISignInView signInView;

    public SignPresenterImpl(ISignInView view) {
        signInView = view;
        init();
    }

    private void init() {
        signInModel = new SignModelImpl();
        signInView.init(signInModel.getLastAccount());
    }

    @Override
    public void doSignUp(Account entity) {
        signInModel.createAccount(entity, new Callback() {
            @Override
            public void onDone(Object object) {
                signInView.onSignUpDone();
            }

            @Override
            public void onError(int code, String desc) {
                signInView.onSignUpError(code, desc);
            }
        });
    }

    /**
     * 处理登录操作
     *
     * @param entity 账户
     */
    @Override
    public void doSignIn(Account entity) {
        signInModel.authAccount(entity, new Callback() {
            @Override
            public void onDone(Object object) {
                signInView.onSignInDone();
            }

            @Override
            public void onError(int code, String desc) {
                signInView.onSignInError(code, desc);
            }
        });
    }
}
