package com.vmloft.develop.app.vnotes.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.vmloft.develop.app.vnotes.app.AppActivity;
import com.vmloft.develop.app.vnotes.app.NavManager;
import com.vmloft.develop.app.vnotes.R;
import com.vmloft.develop.app.vnotes.app.SPManager;
import com.vmloft.develop.library.tools.VMActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends AppActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将主题设置为正常主题
        setTheme(R.style.AppTheme_Default);
        // 判断是否登录，否则跳转到登录界面
        String token = SPManager.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            NavManager.goSignIn(activity);
        }
        setContentView(R.layout.activity_main);

        ButterKnife.bind(activity);

        initView();
    }


    private void initView() {
        setSupportActionBar(getToolbar());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick({R.id.btn_sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.btn_sign_out:
            signOut();
            break;
        }
    }

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
