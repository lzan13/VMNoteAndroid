package com.vmloft.develop.app.vnotes.sign.model;

import com.vmloft.develop.app.vnotes.app.Callback;
import com.vmloft.develop.app.vnotes.bean.UserBean;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录模块数据处理接口
 */
public interface ISignModel {

    /**
     * 创建账户
     */
    void createAccount(UserBean user, Callback callback);

    /**
     * 认证账户，主要是登录获取 token
     */
    void authAccount(UserBean user, Callback callback);

    /**
     * 获取最近登录的一个账户名
     */
    String getLastAccount();
}