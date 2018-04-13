package com.vmloft.develop.app.vnotes;

import com.vmloft.develop.library.tools.VMApplication;

/**
 * Created by lzan13 on 2017/11/23.
 */
public class AppApplication extends VMApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
