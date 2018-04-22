package com.vmloft.develop.app.vmnote.sign;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.vmloft.develop.app.vmnote.app.AppActivity;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.NavManager;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.sign.presenter.ISignPresenter;
import com.vmloft.develop.app.vmnote.sign.presenter.SignPresenterImpl;
import com.vmloft.develop.app.vmnote.sign.view.ISignInView;
import com.vmloft.develop.library.tools.VMFragment;
import com.vmloft.develop.library.tools.widget.VMToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录界面
 */
public class SignActivity extends AppActivity implements ISignInView, VMFragment.FragmentListener {

    private ISignInView signView;
    private ISignPresenter signPresenter;

    private Fragment[] fragments;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.layout_sign_progress) LinearLayout progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(activity);

        signView = this;
        signPresenter = new SignPresenterImpl(signView);
    }

    /**
     * 界面初始化
     */
    @Override
    public void init(String account) {

        signInFragment = SignInFragment.newInstance(account);
        signUpFragment = SignUpFragment.newInstance();

        fragments = new Fragment[]{signInFragment, signUpFragment};
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 设置 ViewPager 缓存个数
        viewPager.setOffscreenPageLimit(1);
        // 添加 ViewPager 页面改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 账户注册
     */
    private void signUp(Account entity) {
        showDialog(true);
        signPresenter.doSignUp(entity);
    }

    /**
     * 注册完成
     */
    @Override
    public void onSignUpDone() {
        showDialog(false);
        VMToast.make("Sign up success!").showDone();
        // 切换到登录界面
        viewPager.setCurrentItem(0, true);
    }

    /**
     * 注册错误
     */
    @Override
    public void onSignUpError(int code, String msg) {
        showDialog(false);
    }

    /**
     * 账户登录
     */
    private void signIn(Account entity) {
        showDialog(true);
        signPresenter.doSignIn(entity);
    }

    /**
     * 请求完成
     */
    @Override
    public void onSignInDone() {
        showDialog(false);
        NavManager.goMain(activity);
    }

    /**
     * 登录出错
     */
    @Override
    public void onSignInError(int code, String msg) {
        showDialog(false);
    }

    /**
     * 控制进度窗的显示
     */
    private void showDialog(boolean show) {
        if (show) {
            progressView.setVisibility(View.VISIBLE);
        } else {
            progressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAction(int action, Object obj) {
        switch (action) {
        case R.id.btn_sign_in:
            signIn((Account) obj);
            break;
        case R.id.btn_sign_in_go:
            viewPager.setCurrentItem(0, true);
            break;
        case R.id.btn_sign_up:
            signUp((Account) obj);
            break;
        case R.id.btn_sign_up_go:
            viewPager.setCurrentItem(1, true);
        }
    }

    /**
     * ViewPager 适配器实现类
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
