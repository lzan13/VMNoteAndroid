package com.vmloft.develop.app.vmnote.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by lzan13 on 2018/4/25.
 * 当前项目 Fragment 的基类，做一些子类公共的工作
 */
public abstract class AppMVPFragment<V, P extends BPresenter<V>> extends AppFragment {
    protected P presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        presenter.attach((V) this);
        super.onActivityCreated(savedInstanceState);
    }

    public abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

}
