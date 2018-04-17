package com.vmloft.develop.app.vnotes.sign.view;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录UI接口
 */
public interface ISignInView {

    /**
     * 初始化方法
     *
     * @param account
     */
    void init(String account);

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
