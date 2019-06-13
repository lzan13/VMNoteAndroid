package com.vmloft.develop.app.vmnote.sign;

import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.base.APresenter;

/**
 * Created by lzan13 on 2018/4/26.
 *
 * 登录、注册相关接口定义契约类
 */
public final class SignContract {

    private SignContract() {
    }

    public interface ISignModel {

        /**
         * 注册
         */
        void signUp(String account, String password, ACallback callback);

        /**
         * 登录
         */
        void signIn(String account, String password, ACallback callback);
    }

    public interface ISignView {

        /**
         * 注册完成
         */
        void onSignUpDone();

        /**
         * 注册错误
         */
        void onSignUpError(int code, String msg);

        /**
         * 登录完成
         */
        void onSignInDone();

        /**
         * 登录错误
         */
        void onSignInError(int code, String msg);
    }

    public static abstract class ISignPresenter<V> extends APresenter<V> {

        /**
         * 尝试注册
         */
        public abstract void doSignUp(String account, String password);

        /**
         * 尝试登录
         */
        public abstract void doSignIn(String account, String password);
    }
}
