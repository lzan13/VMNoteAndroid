package com.vmloft.develop.app.vmnote.common;


import com.vmloft.develop.library.tools.base.VMCallback;

/**
 * Created by lzan13 on 2018/4/16.
 *
 * 全局回调类，可根据需要实现对应回调方法
 */
public class ACallback<T> implements VMCallback<T> {
    @Override
    public void onSuccess(T t) {}

    @Override
    public void onError(int code, String desc) {}

    @Override
    public void onProgress(int progress, String desc) {}
}
