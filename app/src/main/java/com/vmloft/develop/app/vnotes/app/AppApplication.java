package com.vmloft.develop.app.vnotes.app;

import com.vmloft.develop.library.tools.VMApplication;
import com.vmloft.develop.library.tools.VMTools;
import com.vmloft.develop.library.tools.utils.VMTheme;

/**
 * Created by lzan13 on 2017/11/23.
 * 程序入口类
 */
public class AppApplication extends VMApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        VMTools.init(context);
        checkTheme();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkTheme() {
        VMTheme.setNightTheme(SPManager.getInstance().isNight());
    }
}
