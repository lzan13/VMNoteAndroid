package com.vmloft.develop.app.vmnote.sign.presenter;

import com.vmloft.develop.app.vmnote.bean.Account;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录逻辑处理接口
 */
public interface ISignPresenter {

    void doSignUp(Account entity);

    /**
     * 尝试登陆
     *
     * @param entity 账户
     */
    void doSignIn(Account entity);

}
