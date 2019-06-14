package com.vmloft.develop.app.vmnote.ui.settings;

import android.content.DialogInterface;
import android.view.View;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.base.AppActivity;
import com.vmloft.develop.app.vmnote.common.ASignManager;
import com.vmloft.develop.app.vmnote.common.router.ARouter;
import com.vmloft.develop.app.vmnote.common.widget.ADialog;
import com.vmloft.develop.library.tools.utils.VMStr;

import butterknife.OnClick;

/**
 * Create by lzan13 on 2019/05/14
 *
 * 设置界面
 */
public class SettingsActivity extends AppActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initUI() {
        super.initUI();

    }

    @Override
    protected void initData() {
        setTopTitle(R.string.settings);
    }

    @OnClick({R.id.setting_me, R.id.setting_about, R.id.setting_sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_me:
                ARouter.goMeSettings(mActivity);
                break;
            case R.id.setting_about:
//            ARouter.goAboutSetting(mActivity);
                break;
            case R.id.setting_sign_out:
                signOut();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void signOut() {
        String title = VMStr.byRes(R.string.sign_out_hint_title);
        String content = VMStr.byRes(R.string.sign_out_hint_content);
        String cancel = VMStr.byRes(R.string.cancel);
        String ok = VMStr.byRes(R.string.ok);
        ADialog.showAlertDialog(mActivity, title, content, cancel, ok, (DialogInterface dialog, int which) -> {
            ASignManager.getInstance().signOut();
            onFinish();
        });
    }
}
