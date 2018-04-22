package com.vmloft.develop.app.vmnote.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vmloft.develop.app.vmnote.app.AppActivity;
import com.vmloft.develop.app.vmnote.app.NavManager;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.db.DBManager;
import com.vmloft.develop.app.vmnote.home.view.IMainView;
import com.vmloft.develop.app.vmnote.utils.image.IMGLoader;
import com.vmloft.develop.library.tools.utils.VMTheme;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends AppActivity implements IMainView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.img_avatar) ImageView avatarView;
    @BindView(R.id.text_nickname) TextView nicknameView;
    @BindView(R.id.text_account) TextView accountView;


    private String name;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将主题设置为正常主题
        setTheme(R.style.AppTheme);
        // 判断是否登录，否则跳转到登录界面
        String token = SPManager.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            NavManager.goSignIn(activity);
        }
        setContentView(R.layout.activity_main);

        ButterKnife.bind(activity);

        init();
    }

    /**
     * 界面初始化
     */
    private void init() {
        setSupportActionBar(getToolbar());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        name = SPManager.getInstance().getAccount();
        account = DBManager.getInstance().getAccount(name);
        IMGLoader.loadAvatar(activity, account.getAvatar(), avatarView);
        accountView.setText(account.getEmail());
        nicknameView.setText(TextUtils.isEmpty(account.getNickname()) ? account.getName() : account.getNickname());
    }

    @OnClick({R.id.btn_switch_night_theme, R.id.btn_sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.btn_switch_night_theme:
            switchNightTheme();
            break;
        case R.id.btn_sign_out:
            signOut();
            break;
        }
    }

    /**
     * 切换夜间主题
     */
    private void switchNightTheme() {
        boolean isNight = SPManager.getInstance().isNight();
        VMTheme.setNightTheme(!isNight);
        SPManager.getInstance().putNight(!isNight);
        // 重启 Activity
        recreate();
    }

    /**
     * 退出登录
     */
    private void signOut() {
        SPManager.getInstance().putToken("");
        NavManager.goSignIn(activity);
    }

    /**
     * 菜单布局
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 菜单事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_note_all) {

        } else if (id == R.id.nav_note_books) {

        } else if (id == R.id.nav_note_tags) {

        } else if (id == R.id.nav_settings) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
