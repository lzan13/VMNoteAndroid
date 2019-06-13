package com.vmloft.develop.app.vmnote.base;

import com.vmloft.develop.library.tools.base.VMFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lzan13 on 2018/4/25.
 *
 * 当前项目 Fragment 的基类，做一些子类公共的工作
 */
public abstract class AppFragment extends VMFragment {

    private Unbinder unbinder;

    @Override
    protected void init() {
        unbinder = ButterKnife.bind(this, getView());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}