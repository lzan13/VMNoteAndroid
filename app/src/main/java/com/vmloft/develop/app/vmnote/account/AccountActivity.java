package com.vmloft.develop.app.vmnote.account;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.AppActivity;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.common.router.NavRouter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lzan13 on 2018/4/25.
 * 账户信息界面
 */
public class AccountActivity extends AppActivity {

    @BindView(R.id.img_avatar) ImageView avatarView;

    private Toolbar toolbar;

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
    protected void init() {
        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.app_name);

        account = DBManager.getInstance().getAccount(SPManager.getInstance().getAccount());
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
        NavRouter.goSign(activity);
    }
}
