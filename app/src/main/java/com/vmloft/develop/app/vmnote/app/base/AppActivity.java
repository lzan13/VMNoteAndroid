package com.vmloft.develop.app.vmnote.app.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.library.tools.VMActivity;

import butterknife.ButterKnife;

/**
 * Created by lzan13 on 2015/7/4.
 * Activity 的基类，做一些子类公共的工作
 */
public abstract class AppActivity extends VMActivity {

    // 根布局
    private View rootView;
    // Toolbar
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());

        ButterKnife.bind(activity);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toolbar = null;
        rootView = null;
    }

    /**
     * 父类封装 Toolbar
     *
     * @return 返回公用的 Toolbar 控件
     */
    public Toolbar getToolbar() {
        if (toolbar == null) {
            toolbar = findViewById(R.id.widget_toolbar);
        }
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        return toolbar;
    }

    public View getRootView() {
        if (rootView == null) {
            rootView = findViewById(R.id.layout_coordinator);
        }
        return rootView;
    }

    /**
     * 初始化界面 layout_id
     *
     * @return 返回布局 id
     */
    protected abstract int initLayoutId();

    /**
     * 初始化界面
     */
    protected abstract void init();

}
