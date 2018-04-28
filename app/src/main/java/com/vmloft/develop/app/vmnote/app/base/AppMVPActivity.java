package com.vmloft.develop.app.vmnote.app.base;

import android.os.Bundle;

/**
 * Created by lzan13 on 2015/7/4.
 * Activity 的基类，做一些子类公共的工作
 */
public abstract class AppMVPActivity<V, P extends BPresenter<V>> extends AppActivity {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = createPresenter();
        presenter.attach((V) this);
        super.onCreate(savedInstanceState);
    }

    public abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

}
