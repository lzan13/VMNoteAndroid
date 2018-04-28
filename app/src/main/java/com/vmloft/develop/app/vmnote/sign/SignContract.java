package com.vmloft.develop.app.vmnote.sign;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.base.BPresenter;
import com.vmloft.develop.app.vmnote.bean.Account;

/**
 * Created by lzan13 on 2018/4/26.
 * 登录、注册相关接口定义契约类
 */
public final class SignContract {

    private SignContract() {}

    public interface ISignModel {
        /**
         * 创建账户
         */
        void createAccount(Account entity, Callback callback);

        /**
         * 认证账户，主要是登录获取 token
         */
        void authAccount(Account entity, Callback callback);
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

    public static abstract class ISignPresenter<V> extends BPresenter<V> {

        public abstract void doSignUp(Account entity);

        /**
         * 尝试登陆
         *
         * @param entity 账户
         */
        public abstract void doSignIn(Account entity);

    }
}
