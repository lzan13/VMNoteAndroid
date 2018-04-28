package com.vmloft.develop.app.vmnote.app;

import com.vmloft.develop.library.tools.utils.VMSPUtil;

/**
 * Created by lzan13 on 2018/4/13.
 * SharePreference 操作管理类
 */
public class SPManager {

    final String ACCOUNT_ID = "account_id";
    final String ACCOUNT_NAME = "account_name";
    final String ACCOUNT_TOKEN = "account_token";
    final String NIGHT_THEME = "night_theme";
    final String SYNC_KEY = "sync_key";

    private static SPManager instance;

    private SPManager() {}

    public static SPManager getInstance() {
        if (instance == null) {
            instance = new SPManager();
        }
        return instance;
    }

    /**
     * account 信息获取和保存
     */
    public String getAccountId() {
        return (String) VMSPUtil.get(ACCOUNT_ID, "");
    }

    public void putAccountId(String id) {
        VMSPUtil.put(ACCOUNT_ID, id);
    }

    public String getAccountName() {
        return (String) VMSPUtil.get(ACCOUNT_NAME, "");
    }

    public void putAccountName(String name) {
        VMSPUtil.put(ACCOUNT_NAME, name);
    }

    /**
     * 夜间模式
     */
    public boolean isNight() {
        return (boolean) VMSPUtil.get(NIGHT_THEME, false);
    }

    public void putNight(boolean isNight) {
        VMSPUtil.put(NIGHT_THEME, isNight);
    }

    /**
     * token 的获取和保存
     */
    public String getToken() {
        return (String) VMSPUtil.get(ACCOUNT_TOKEN, "");
    }

    public void putToken(String token) {
        VMSPUtil.put(ACCOUNT_TOKEN, token);
    }

    /**
     * 同步时间戳的获取
     */
    public String getSyncKey() {
        return (String) VMSPUtil.get(SYNC_KEY, "");
    }

    public void putSyncKey(String syncKey) {
        VMSPUtil.put(SYNC_KEY, syncKey);
    }
}

