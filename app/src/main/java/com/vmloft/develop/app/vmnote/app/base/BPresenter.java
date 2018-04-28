package com.vmloft.develop.app.vmnote.app.base;

import java.lang.ref.WeakReference;

/**
 * Created by lzan13 on 2018/4/26.
 * Presenter 基类，主要处理 P层 对 V对象 的弱引用
 */
public abstract class BPresenter<V> {
    // 定义 Presenter 对 View 持有若引用
    private WeakReference<V> viewRef;

    /**
     * 绑定 View
     */
    public void attach(V view) {
        viewRef = new WeakReference<V>(view);
    }

    /**
     * 解绑 View
     */
    public void detach() {
        if (isAttach()) {
            viewRef.clear();
            viewRef = null;
        }
    }

    /**
     * 获取 Presenter 绑定的 View
     */
    public V obtainView() {
        return isAttach() ? viewRef.get() : null;
    }

    /**
     * 是否已经绑定 View
     */
    protected boolean isAttach() {
        return viewRef != null && viewRef.get() != null;
    }
}
