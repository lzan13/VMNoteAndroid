package com.vmloft.develop.app.vmnote.home;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vmloft.develop.app.vmnote.app.AppActivity;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.home.presenter.IMainPresenter;
import com.vmloft.develop.app.vmnote.home.presenter.MainPresenterImpl;
import com.vmloft.develop.app.vmnote.home.view.IMainView;
import com.vmloft.develop.app.vmnote.common.router.NavRouter;
import com.vmloft.develop.app.vmnote.common.image.IMGLoader;
import com.vmloft.develop.library.tools.utils.VMDateUtil;
import com.vmloft.develop.library.tools.utils.VMStrUtil;
import com.vmloft.develop.library.tools.utils.VMTheme;
import com.vmloft.develop.library.tools.widget.VMToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends AppActivity implements IMainView {

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.layout_note_all) LinearLayout noteAllMenu;
    @BindView(R.id.layout_note_tags) LinearLayout noteTagsMenu;
    @BindView(R.id.layout_note_trash) LinearLayout noteTrashMenu;
    @BindView(R.id.img_cover) ImageView coverView;
    @BindView(R.id.img_avatar) ImageView avatarView;
    @BindView(R.id.text_nickname) TextView nicknameView;
    @BindView(R.id.text_account) TextView accountView;
    @BindView(R.id.img_night_theme) ImageView nightThemeIconView;
    @BindView(R.id.text_sync_time) TextView syncTimeView;
    private Toolbar toolbar;


    private IMainView mainView;
    private IMainPresenter mainPresenter;

    private String account;
    private Account entity;
    private int[] menus = {R.string.note_all, R.string.note_tags, R.string.note_trash};
    private int currIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 将主题设置为正常主题
        setTheme(R.style.AppTheme);
        // 判断是否登录，否则跳转到登录界面
        String token = SPManager.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            NavRouter.goSign(activity);
            return;
        }
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化界面 layout_id
     */
    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化界面控件
     */
    @Override
    protected void init() {
        mainView = this;
        mainPresenter = new MainPresenterImpl(mainView);

        toolbar = getToolbar();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        account = SPManager.getInstance().getAccount();
        entity = DBManager.getInstance().getAccount(account);
        if (entity != null) {
            IMGLoader.loadBigPhoto(activity, entity.getCover(), coverView);
            IMGLoader.loadAvatar(activity, entity.getAvatar(), avatarView);
            accountView.setText(entity.getEmail());
            nicknameView.setText(TextUtils.isEmpty(entity.getNickname()) ? entity.getName() : entity
                    .getNickname());
        }
        initNightThemeState();
        selectedMenu(0);
    }

    @OnClick({R.id.img_avatar, R.id.layout_note_all, R.id.layout_note_tags, R.id.layout_note_trash,
                     R.id.layout_night_theme, R.id.layout_settings, R.id.layout_sync, R.id.fab_add})
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.img_avatar:
            NavRouter.goAccount(activity);
            break;
        case R.id.layout_note_all:
            selectedMenu(0);
            break;
        case R.id.layout_note_tags:
            selectedMenu(1);
            break;
        case R.id.layout_note_trash:
            selectedMenu(2);
            break;
        case R.id.layout_night_theme:
            switchNightTheme();
            break;
        case R.id.layout_settings:
            break;
        case R.id.layout_sync:
            syncNote();
            break;
        case R.id.fab_add:
            NavRouter.goNoteEditor(activity);
            break;
        }
    }

    /**
     * 开始同步
     */
    public void syncNote() {
        mainPresenter.onSync();
    }

    /**
     * 同步完成
     */
    @Override
    public void syncDone() {
        String syncKey = SPManager.getInstance().getSyncKey();
        String syncTime = VMDateUtil.long2DateTimeNoYear(VMDateUtil.getMilliFormUTC(syncKey));
        String str = String.format(VMStrUtil.strByResId(R.string.note_sync), syncTime);
        syncTimeView.setText(str);
        VMToast.make(str).showDone();
    }

    /**
     * 同步失败
     */
    @Override
    public void syncError(int code, String desc) {
        VMToast.make(desc).showError();
    }

    /**
     * 检查菜单选中状态
     */
    private void selectedMenu(int index) {
        drawer.closeDrawer(GravityCompat.START);
        if (currIndex == index) {
            return;
        }
        currIndex = index;
        if (currIndex == 0) {
            noteAllMenu.setSelected(true);
            noteTagsMenu.setSelected(false);
            noteTrashMenu.setSelected(false);
        } else if (currIndex == 1) {
            noteAllMenu.setSelected(false);
            noteTagsMenu.setSelected(true);
            noteTrashMenu.setSelected(false);
        } else if (currIndex == 2) {
            noteAllMenu.setSelected(false);
            noteTagsMenu.setSelected(false);
            noteTrashMenu.setSelected(true);
        }
        toolbar.setTitle(menus[currIndex]);
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
     * 加载主题状态
     */
    private void initNightThemeState() {
        boolean isNight = SPManager.getInstance().isNight();
        if (isNight) {
            nightThemeIconView.setImageResource(R.drawable.ic_night);
        } else {
            nightThemeIconView.setImageResource(R.drawable.ic_sunny);
        }
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
