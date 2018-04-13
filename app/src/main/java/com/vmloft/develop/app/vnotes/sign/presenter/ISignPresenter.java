package com.vmloft.develop.app.vnotes.sign.presenter;

/**
 * Created by lzan13 on 2017/11/23.
 */
public interface ISignPresenter {
    /**
     * 尝试登陆
     *
     * @param account 账户
     * @param password 密码
     */
    void doSignIn(String account, String password);

}
