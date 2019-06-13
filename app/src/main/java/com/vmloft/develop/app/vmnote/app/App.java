package com.vmloft.develop.app.vmnote.app;


import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import com.vmloft.develop.app.vmnote.bean.AUser;
import com.vmloft.develop.app.vmnote.common.AConstants;
import com.vmloft.develop.app.vmnote.common.ASPManager;
import com.vmloft.develop.library.tools.VMTools;
import com.vmloft.develop.library.tools.base.VMApp;
import com.vmloft.develop.library.tools.utils.VMTheme;

/**
 * Created by lzan13 on 2017/11/23.
 * 程序入口类
 */
public class App extends VMApp {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        VMTools.init(context);

        checkTheme();

        // 初始化 LeanCloud
        initLeanCloud();
    }


    /**
     * 初始化 LeanCloud
     */
    private void initLeanCloud() {
//        AVObject.registerSubclass(AMatch.class);
        AVUser.registerSubclass(AUser.class);
        AVUser.alwaysUseSubUserClass(AUser.class);
        AVOSCloud.initialize(context, AConstants.APP_LC_ID, AConstants.APP_LC_KEY);
    }

    private void checkTheme() {
        VMTheme.setNightTheme(ASPManager.getInstance().isNight());
    }
}
