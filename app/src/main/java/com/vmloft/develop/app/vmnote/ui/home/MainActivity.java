package com.vmloft.develop.app.vmnote.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.bean.AUser;
import com.vmloft.develop.app.vmnote.common.APermissionManager;
import com.vmloft.develop.app.vmnote.common.ASPManager;
import com.vmloft.develop.app.vmnote.base.AppFragment;
import com.vmloft.develop.app.vmnote.base.AppMVPActivity;
import com.vmloft.develop.app.vmnote.common.ASignManager;
import com.vmloft.develop.app.vmnote.common.image.ALoader;
import com.vmloft.develop.app.vmnote.ui.home.presenter.MainPresenterImpl;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.IMainView;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.IMainPresenter;
import com.vmloft.develop.app.vmnote.common.router.ARouter;
import com.vmloft.develop.library.tools.picker.IPictureLoader;
import com.vmloft.develop.library.tools.utils.VMStr;
import com.vmloft.develop.library.tools.utils.VMTheme;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends AppMVPActivity<IMainView, IMainPresenter<IMainView>> implements IMainView {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_drawer_cover_iv)
    ImageView mCoverView;
    @BindView(R.id.main_drawer_avatar_iv)
    ImageView mAvatarView;
    @BindView(R.id.main_drawer_nickname_tv)
    TextView mNicknameView;

    @BindView(R.id.main_drawer_all_ll)
    LinearLayout mAllMenu;
    @BindView(R.id.main_drawer_category_ll)
    LinearLayout mCategoryMenu;
    @BindView(R.id.main_drawer_trash_ll)
    LinearLayout mTrashMenu;
    @BindView(R.id.main_drawer_theme_icon)
    ImageView mThemeIconView;

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

        if (!ASignManager.getInstance().isSingIn()) {
            return;
        }
        APermissionManager.getInstance().requestPermissions(mActivity);
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
        setTopIcon(R.drawable.ic_menu);
        setTopTitle(mMenus[mCurrIndex]);
        setTopIconListener(v -> {
            mDrawerLayout.openDrawer(GravityCompat.START);
        });

        initThemeState();

        initFragment();

        mAllMenu.setSelected(true);
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
            // 加载封面背景
            String url = user.getAvatar() != null ? user.getAvatar().getUrl() : "";
            IPictureLoader.Options options = new IPictureLoader.Options(url);
            options.isBlur = true;
            ALoader.load(mActivity, options, mCoverView);

            // 加载头像
            options = new IPictureLoader.Options(url);
            options.isCircle = true;
            ALoader.load(mActivity, options, mAvatarView);
            mNicknameView.setText(VMStr.isEmpty(user.getNickname()) ? user.getUsername() : user.getNickname());
        }
    }

    @OnClick({
            R.id.main_drawer_cover_iv, R.id.main_drawer_avatar_iv, R.id.main_drawer_all_ll, R.id.main_drawer_category_ll, R.id.main_drawer_trash_ll,
            R.id.main_drawer_theme_ll, R.id.main_drawer_settings_ll
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_drawer_cover_iv:
            case R.id.main_drawer_avatar_iv:
                ARouter.goMeSettings(mActivity);
                break;
            case R.id.main_drawer_all_ll:
                changeMenuSelected(0);
                break;
            case R.id.main_drawer_category_ll:
                changeMenuSelected(1);
                break;
            case R.id.main_drawer_trash_ll:
                changeMenuSelected(2);
                break;
            case R.id.main_drawer_settings_ll:
                ARouter.goSettings(mActivity);
                break;
            case R.id.main_drawer_theme_ll:
                changeTheme();
                break;
        }
    }

    /**
     * 检查菜单选中状态
     */
    private void changeMenuSelected(int index) {
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
            mAllMenu.setSelected(true);
            mCategoryMenu.setSelected(false);
            mTrashMenu.setSelected(false);
        } else if (mCurrIndex == 1) {
            mAllMenu.setSelected(false);
            mCategoryMenu.setSelected(true);
            mTrashMenu.setSelected(false);
        } else if (mCurrIndex == 2) {
            mAllMenu.setSelected(false);
            mCategoryMenu.setSelected(false);
            mTrashMenu.setSelected(true);
        }
        setTopTitle(mMenus[mCurrIndex]);
    }

    /**
     * 切换夜间主题
     */
    private void changeTheme() {
        boolean isNight = ASPManager.getInstance().isNight();
        VMTheme.setNightTheme(!isNight);
        ASPManager.getInstance().putNight(!isNight);
        // 重启 Activity
        ARouter.goMain(mActivity);
        overridePendingTransition(R.anim.vm_fade_in, R.anim.vm_fade_out);
    }

    /**
     * 加载主题状态
     */
    private void initThemeState() {
        boolean isNight = ASPManager.getInstance().isNight();
        if (isNight) {
            mThemeIconView.setImageResource(R.drawable.ic_theme_night);
        } else {
            mThemeIconView.setImageResource(R.drawable.ic_theme_sunny);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            ARouter.goLauncher(mActivity);
        }
    }
}
