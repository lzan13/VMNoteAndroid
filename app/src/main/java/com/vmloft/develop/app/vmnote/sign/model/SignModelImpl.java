package com.vmloft.develop.app.vmnote.sign.model;

import com.vmloft.develop.app.vmnote.common.api.AccountApi;
import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.Account;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录相关数据处理实现
 */
public class SignModelImpl implements ISignModel {

    /**
     * 创建账户
     */
    @Override
    public void createAccount(Account entity, Callback callback) {
        AccountApi.getInstance().createAccount(entity, callback);
    }

    /**
     * 认证账户
     */
    @Override
    public void authAccount(Account entity, final Callback callback) {
        AccountApi.getInstance().authAccount(entity, callback);
    }

    @Override
    public String getLastAccount() {
        return SPManager.getInstance().getAccount();
    }
}
