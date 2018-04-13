package com.vmloft.develop.app.vnotes;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vmloft.develop.library.tools.VMActivity;

/**
 * Created by lzan13 on 2015/7/4.
 * Activity 的基类，做一些子类公共的工作
 */
public class AppActivity extends VMActivity {

    // 根布局
    private View rootView;

    // Toolbar
    private Toolbar toolbar;

    protected AlertDialog.Builder dialog;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        return toolbar;
    }

    public View getRootView() {
        if (rootView == null) {
            rootView = findViewById(R.id.layout_coordinator);
        }
        return rootView;
    }
}
