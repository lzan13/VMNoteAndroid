package com.vmloft.develop.app.vnotes.sign.model;

import com.vmloft.develop.library.tools.utils.VMSPUtil;

/**
 * Created by lzan13 on 2017/11/23.
 */
public class SignInModel implements ISign {

    @Override
    public void saveAccount(String account) {
        VMSPUtil.put("account", account);
    }

    @Override
    public String getAccount() {
        return (String) VMSPUtil.get("account", "");
    }

    @Override
    public void saveToken(String token) {
        VMSPUtil.put("token", token);
    }
}
