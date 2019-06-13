package com.vmloft.develop.app.vmnote.base;

import android.os.Bundle;

/**
 * Created by lzan13 on 2015/7/4.
 *
 * Activity 的 MVP 基类，做一些子类公共的工作
 */
public abstract class AppMVPActivity<V, P extends APresenter<V>> extends AppActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attach((V) this);
        super.onCreate(savedInstanceState);
    }

    public abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

}
