package com.vmloft.develop.app.vmnote.sign.presenter;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignModel;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignView;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignPresenter;
import com.vmloft.develop.app.vmnote.sign.model.SignModelImpl;


/**
 * Created by lzan13 on 2017/11/23.
 * 登录处理实现
 */
public class SignPresenterImpl extends ISignPresenter<ISignView> {
    private ISignModel signModel;

    public SignPresenterImpl() {
        signModel = new SignModelImpl();
    }

    @Override
    public void doSignUp(Account entity) {
        signModel.createAccount(entity, new Callback() {
            @Override
            public void onDone(Object object) {
                obtainView().onSignUpDone();
            }

            @Override
            public void onError(int code, String desc) {
                obtainView().onSignUpError(code, desc);
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
        signModel.authAccount(entity, new Callback() {
            @Override
            public void onDone(Object object) {
                obtainView().onSignInDone();
            }

            @Override
            public void onError(int code, String desc) {
                obtainView().onSignInError(code, desc);
            }
        });
    }
}
