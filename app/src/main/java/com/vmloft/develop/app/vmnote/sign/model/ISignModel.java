package com.vmloft.develop.app.vmnote.sign.model;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.Account;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录模块数据处理接口
 */
public interface ISignModel {

    /**
     * 创建账户
     */
    void createAccount(Account entity, Callback callback);

    /**
     * 认证账户，主要是登录获取 token
     */
    void authAccount(Account entity, Callback callback);

    /**
     * 获取最近登录的一个账户名
     */
    String getLastAccount();
}