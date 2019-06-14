package com.vmloft.develop.app.vmnote.base;

import android.view.View;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.common.ASPManager;
import com.vmloft.develop.app.vmnote.common.utils.AUtils;
import com.vmloft.develop.library.tools.base.VMBActivity;
import com.vmloft.develop.library.tools.utils.VMDimen;
import com.vmloft.develop.library.tools.widget.VMTopBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lzan13 on 2015/7/4.
 *
 * Activity 的基类，做一些子类公共的工作
 */
public abstract class AppActivity extends VMBActivity {

    protected Unbinder unbinder;

    // 统一的 TopBar
    protected VMTopBar mTopBar;
    protected View mTopSpaceView;

    @Override
    protected void initUI() {
        boolean isNight = ASPManager.getInstance().isNight();
        // 设置状态栏，这个要和主题模式反着来
        AUtils.setDarkStatusBar(mActivity, !isNight);

        unbinder = ButterKnife.bind(mActivity);

        setupTopBar();
    }

    /**
     * 装载 TopBar
     */
    protected void setupTopBar() {
        mTopSpaceView = findViewById(R.id.common_top_space);
        if (mTopSpaceView != null) {
            // 设置状态栏透明主题时，布局整体会上移，所以给头部 View 设置 StatusBar 的高度
            mTopSpaceView.getLayoutParams().height = VMDimen.getStatusBarHeight();
        }

        mTopBar = findViewById(R.id.common_top_bar);
        setTopIcon(R.drawable.vm_ic_arrow_left);
        setTopIconListener((View v) -> {
            onBackPressed();
        });

    }

    /**
     * 通用的获取 TopBar 方法
     */
    protected VMTopBar getTopBar() {
        return mTopBar;
    }

    /**
     * 设置图标
     */
    protected void setTopIcon(int resId) {
        if (mTopBar == null) {
            return;
        }
        mTopBar.setIcon(resId);
    }

    /**
     * 设置标题
     */
    protected void setTopTitle(int resId) {
        if (mTopBar == null) {
            return;
        }
        mTopBar.setTitle(resId);
    }

    /**
     * 设置标题
     */
    protected void setTopTitle(String title) {
        if (mTopBar == null) {
            return;
        }
        mTopBar.setTitle(title);
    }

    /**
     * 设置标题颜色
     */
    protected void setTopTitleColor(int resId) {
        if (mTopBar == null) {
            return;
        }
        mTopBar.setTitleColor(resId);
    }

    /**
     * 设置图标点击监听
     */
    protected void setTopIconListener(View.OnClickListener listener) {
        if (mTopBar == null) {
            return;
        }
        mTopBar.setIconListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
