package com.vmloft.develop.app.vmnote.account;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.base.AppActivity;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.common.image.IMGLoader;
import com.vmloft.develop.library.tools.utils.VMStr;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lzan13 on 2018/4/25.
 * 账户信息界面
 */
public class AccountActivity extends AppActivity {

    @BindView(R.id.img_cover) ImageView coverView;
    @BindView(R.id.img_avatar) ImageView avatarView;
    @BindView(R.id.text_nickname) TextView nicknameView;
    @BindView(R.id.text_account) TextView accountView;

    private Toolbar toolbar;

    private String accountName;
    private Account account;

    /**
     * 初始化界面 layout_id
     */
    @Override
    protected int initLayoutId() {
        return R.layout.activity_account;
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.app_name);

        accountName = SPManager.getInstance().getAccountName();
        account = DBManager.getInstance().getAccount(accountName);
        if (account != null) {
            IMGLoader.loadBigPhoto(activity, account.getCover(), coverView);
            IMGLoader.loadAvatar(activity, account.getAvatar(), avatarView);
            accountView.setText(account.getEmail());
            nicknameView.setText(VMStr.isEmpty(account.getNickname()) ? account.getName() : account
                    .getNickname());
        }
    }

    @OnClick({R.id.btn_sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.btn_sign_out:
            signOut();
            break;
        }
    }

    /**
     * 退出登录
     */
    private void signOut() {
        SPManager.getInstance().putToken("");
        DBManager.getInstance().deleteAccount(account);
        onFinish();
    }
}
