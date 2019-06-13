package com.vmloft.develop.app.vmnote.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.bean.AUser;
import com.vmloft.develop.app.vmnote.common.ASPManager;
import com.vmloft.develop.app.vmnote.base.AppFragment;
import com.vmloft.develop.app.vmnote.base.AppMVPActivity;
import com.vmloft.develop.app.vmnote.common.ASignManager;
import com.vmloft.develop.app.vmnote.common.image.ALoader;
import com.vmloft.develop.app.vmnote.common.utils.AUtils;
import com.vmloft.develop.app.vmnote.home.presenter.MainPresenterImpl;
import com.vmloft.develop.app.vmnote.home.MainContract.IMainView;
import com.vmloft.develop.app.vmnote.home.MainContract.IMainPresenter;
import com.vmloft.develop.app.vmnote.common.router.ARouter;
import com.vmloft.develop.library.tools.utils.VMTheme;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends AppMVPActivity<IMainView, IMainPresenter<IMainView>> implements IMainView {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.layout_note_all)
    LinearLayout noteAllMenu;
    @BindView(R.id.layout_note_books)
    LinearLayout noteBooksMenu;
    @BindView(R.id.layout_note_trash)
    LinearLayout noteTrashMenu;
    @BindView(R.id.img_cover)
    ImageView coverView;
    @BindView(R.id.img_avatar)
    ImageView avatarView;
    @BindView(R.id.text_nickname)
    TextView nicknameView;
    @BindView(R.id.text_account)
    TextView accountView;
    @BindView(R.id.img_night_theme)
    ImageView nightThemeIconView;

    private int[] mMenus = {R.string.note_all, R.string.note_books, R.string.note_trash};
    private int mCurrIndex = 0;
    private AppFragment[] fragments;
    private DisplayFragment displayFragment;
    private CategoryFragment categoryFragment;
    private TrashFragment trashFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 将主题设置为正常主题
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // 设置深色状态栏
        AUtils.setDarkStatusBar(mActivity, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 判断是否登录，否则跳转到登录界面
        if (!ASignManager.getInstance().isSingIn()) {
            ARouter.goSign(mActivity);
        }
    }

    @Override
    public IMainPresenter<IMainView> createPresenter() {
        return new MainPresenterImpl();
    }

    /**
     * 初始化界面 layout_id
     */
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化界面控件
     */
    @Override
    protected void initUI() {
        super.initUI();
        getTopBar().setTitle(mMenus[mCurrIndex]);

        initNightThemeState();

        initFragment();

        noteAllMenu.setSelected(true);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mPresenter.onLoadAccount();
    }

    private void initFragment() {
        displayFragment = new DisplayFragment();
        categoryFragment = new CategoryFragment();
        trashFragment = new TrashFragment();
        fragments = new AppFragment[]{displayFragment, categoryFragment, trashFragment};
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_fragment_container, displayFragment);
        ft.add(R.id.main_fragment_container, categoryFragment);
        ft.add(R.id.main_fragment_container, trashFragment);
        ft.hide(trashFragment);
        ft.hide(categoryFragment);
        ft.show(displayFragment);
        ft.commit();
    }

    @Override
    public void loadAccountDone(AUser user) {
        if (user != null) {
            String url = user.getAvatar() != null ? user.getAvatar().getUrl() : "";
            ALoader.Options options = new ALoader.Options(url);
            options.isBlur = true;
            ALoader.load(mActivity, options, coverView);

            options = new ALoader.Options(url);
//            options.isBlur = true;
            ALoader.load(mActivity, options, avatarView);
            accountView.setText(user.getEmail());
            nicknameView.setText(TextUtils.isEmpty(user.getNickname()) ? user.getUsername() : user.getNickname());
        }
    }

    @OnClick({
            R.id.img_avatar, R.id.layout_note_all, R.id.layout_note_books, R.id.layout_note_trash,
            R.id.layout_night_theme, R.id.layout_settings
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                ARouter.goAccount(mActivity);
                break;
            case R.id.layout_note_all:
                selectedMenu(0);
                break;
            case R.id.layout_note_books:
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
        }
    }

    /**
     * 检查菜单选中状态
     */
    private void selectedMenu(int index) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (mCurrIndex == index) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!fragments[index].isAdded()) {
            ft.add(R.id.main_fragment_container, fragments[index]);
        }
        ft.hide(fragments[mCurrIndex]);
        ft.show(fragments[index]);
        ft.commit();

        mCurrIndex = index;
        if (mCurrIndex == 0) {
            noteAllMenu.setSelected(true);
            noteBooksMenu.setSelected(false);
            noteTrashMenu.setSelected(false);
        } else if (mCurrIndex == 1) {
            noteAllMenu.setSelected(false);
            noteBooksMenu.setSelected(true);
            noteTrashMenu.setSelected(false);
        } else if (mCurrIndex == 2) {
            noteAllMenu.setSelected(false);
            noteBooksMenu.setSelected(false);
            noteTrashMenu.setSelected(true);
        }
        getTopBar().setTitle(mMenus[mCurrIndex]);
    }

    /**
     * 切换夜间主题
     */
    private void switchNightTheme() {
        boolean isNight = ASPManager.getInstance().isNight();
        VMTheme.setNightTheme(!isNight);
        ASPManager.getInstance().putNight(!isNight);
        // 重启 Activity
        recreate();
    }

    /**
     * 加载主题状态
     */
    private void initNightThemeState() {
        boolean isNight = ASPManager.getInstance().isNight();
        if (isNight) {
            nightThemeIconView.setImageResource(R.drawable.ic_theme_night);
        } else {
            nightThemeIconView.setImageResource(R.drawable.ic_theme_sunny);
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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
