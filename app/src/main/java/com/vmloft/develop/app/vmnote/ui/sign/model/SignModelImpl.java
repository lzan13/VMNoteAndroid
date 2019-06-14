package com.vmloft.develop.app.vmnote.ui.sign.model;

import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.common.ASignManager;
import com.vmloft.develop.app.vmnote.ui.sign.SignContract;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录相关数据处理实现
 */
public class SignModelImpl implements SignContract.ISignModel {

    /**
     * 注册账户
     */
    @Override
    public void signUp(String account, String password, ACallback callback) {
        ASignManager.getInstance().signUp(account, password, callback);
    }

    /**
     * 登录账户
     */
    @Override
    public void signIn(String account, String password, final ACallback callback) {
        ASignManager.getInstance().signIn(account, password, callback);
    }
}
