package com.vmloft.develop.app.vmnote.ui.settings;

import android.view.View;
import android.widget.EditText;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.base.AppActivity;
import com.vmloft.develop.app.vmnote.bean.AUser;
import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.common.ASignManager;
import com.vmloft.develop.app.vmnote.common.AUMSManager;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMStr;

import butterknife.BindView;

/**
 * Create by lzan13 on 2019/5/12 22:20
 *
 * 修改个人信息界面之签名
 */
public class MeNicknameActivity extends AppActivity {

    @BindView(R.id.me_nickname_et) EditText mNicknameET;
    // 个人用户
    private AUser mUser;

    @Override
    protected int layoutId() {
        return R.layout.activity_me_nickname;
    }

    @Override
    protected void initUI() {
        super.initUI();
        getTopBar().setEndBtnListener(VMStr.byRes(R.string.finish), (View view) -> { saveNickname();});
    }

    @Override
    protected void initData() {
        setTopTitle(R.string.me_info);
        mUser = ASignManager.getInstance().getCurrentUser();
        mNicknameET.setText(mUser.getNickname());
    }

    /**
     * 保存头像
     */
    public void saveNickname() {
        String nickname = mNicknameET.getText().toString().trim();
        mUser.setNickname(nickname);
        AUMSManager.getInstance().saveUserInfo(mUser, new ACallback<AUser>() {
            @Override
            public void onSuccess(AUser user) {
                VMLog.d("用户信息保存成功");
                onFinish();
            }

            @Override
            public void onError(int code, String desc) {
                VMLog.e("用户信息保存失败 %d %s", code, desc);
            }
        });
    }
}
