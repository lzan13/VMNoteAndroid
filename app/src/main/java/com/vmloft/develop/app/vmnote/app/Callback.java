package com.vmloft.develop.app.vmnote.app;

import com.vmloft.develop.library.tools.VMCallback;

/**
 * Created by lzan13 on 2018/4/16.
 * 全局抽象回调类，可根据需要实现对应回调方法
 */
public abstract class Callback implements VMCallback {
    @Override
    public void onDone(Object object) {}

    @Override
    public void onError(int code, String desc) {}

    @Override
    public void onProgress(int progress, String desc) {}
}
