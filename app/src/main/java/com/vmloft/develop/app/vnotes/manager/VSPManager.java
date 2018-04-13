package com.vmloft.develop.app.vnotes.manager;

import com.vmloft.develop.library.tools.utils.VMSPUtil;

/**
 * Created by lzan13 on 2018/4/13.
 * SharePreference 管理类
 */
public class VSPManager {

    final String TOKEN = "token";

    private static VSPManager instance;

    private VSPManager() {}

    public static VSPManager getInstance() {
        if (instance == null) {
            instance = new VSPManager();
        }
        return instance;
    }

    public String getToken() {
        return (String) VMSPUtil.get(TOKEN, "");
    }

    public void saveToken(String token) {
        VMSPUtil.put(TOKEN, token);
    }
}
