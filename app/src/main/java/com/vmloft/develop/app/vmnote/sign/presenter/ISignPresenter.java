package com.vmloft.develop.app.vmnote.sign.presenter;

import com.vmloft.develop.app.vmnote.bean.UserBean;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录逻辑处理接口
 */
public interface ISignPresenter {

    void doSignUp(UserBean user);

    /**
     * 尝试登陆
     *
     * @param user 账户
     */
    void doSignIn(UserBean user);

}
