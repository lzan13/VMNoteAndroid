package com.vmloft.develop.app.vmnote.app;

import com.vmloft.develop.app.vmnote.app.base.AppActivity;
import com.vmloft.develop.library.tools.VMApp;
import com.vmloft.develop.library.tools.VMTools;
import com.vmloft.develop.library.tools.utils.VMTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzan13 on 2017/11/23.
 * 程序入口类
 */
public class App extends VMApp {

    @Override
    public void onCreate() {
        super.onCreate();

        VMTools.init(context);
        checkTheme();

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkTheme() {
        VMTheme.setNightTheme(SPManager.getInstance().isNight());
    }
}
