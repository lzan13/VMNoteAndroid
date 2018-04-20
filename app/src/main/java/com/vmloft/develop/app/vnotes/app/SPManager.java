package com.vmloft.develop.app.vnotes.app;

import com.vmloft.develop.library.tools.utils.VMSPUtil;

/**
 * Created by lzan13 on 2018/4/13.
 * SharePreference 操作管理类
 */
public class SPManager {

    final String ACCOUNT = "account";
    final String NIGHT = "night";
    final String TOKEN = "token";

    private static SPManager instance;

    private SPManager() {}

    public static SPManager getInstance() {
        if (instance == null) {
            instance = new SPManager();
        }
        return instance;
    }

    /**
     * account 的获取和保存
     */
    public String getAccount() {
        return (String) VMSPUtil.get(ACCOUNT, "");
    }

    public void putAccount(String account) {
        VMSPUtil.put(ACCOUNT, account);
    }

    /**
     * 夜间模式
     */
    public boolean isNight() {
        return (boolean) VMSPUtil.get(NIGHT, false);
    }

    public void putNigiht(boolean isNight) {
        VMSPUtil.put(NIGHT, isNight);
    }

    /**
     * token 的获取和保存
     */
    public String getToken() {
        return (String) VMSPUtil.get(TOKEN, "");
    }

    public void putToken(String token) {
        VMSPUtil.put(TOKEN, token);
    }
}

