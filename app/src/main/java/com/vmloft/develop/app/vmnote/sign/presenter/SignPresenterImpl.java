package com.vmloft.develop.app.vmnote.sign.presenter;

import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignModel;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignView;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignPresenter;
import com.vmloft.develop.app.vmnote.sign.model.SignModelImpl;

/**
 * Created by lzan13 on 2017/11/23.
 *
 * 登录处理实现
 */
public class SignPresenterImpl extends ISignPresenter<ISignView> {
    private ISignModel mSignModel;

    public SignPresenterImpl() {
        mSignModel = new SignModelImpl();
    }

    @Override
    public void doSignUp(String account, String password) {
        mSignModel.signUp(account, password, new ACallback() {
            @Override
            public void onSuccess(Object object) {
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
     */
    @Override
    public void doSignIn(String account, String password) {
        mSignModel.signIn(account, password, new ACallback() {
            @Override
            public void onSuccess(Object object) {
                obtainView().onSignInDone();
            }

            @Override
            public void onError(int code, String desc) {
                obtainView().onSignInError(code, desc);
            }
        });
    }
}
