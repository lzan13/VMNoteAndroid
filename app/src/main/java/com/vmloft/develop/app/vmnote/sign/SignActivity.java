package com.vmloft.develop.app.vmnote.sign;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.base.AppFragment;
import com.vmloft.develop.app.vmnote.base.AppMVPActivity;
import com.vmloft.develop.app.vmnote.common.router.ARouter;
import com.vmloft.develop.app.vmnote.common.utils.AUtils;
import com.vmloft.develop.app.vmnote.sign.presenter.SignPresenterImpl;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignView;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignPresenter;
import com.vmloft.develop.library.tools.widget.toast.VMToast;

import butterknife.BindView;

/**
 * 登录界面
 */
public class SignActivity extends AppMVPActivity<ISignView, ISignPresenter<ISignView>> implements ISignView {

    @BindView(R.id.layout_sign_progress)
    LinearLayout progressView;

    private AppFragment[] fragments;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    private int currIndex = 0;

    /**
     * 初始化界面 layout_id
     *
     * @return 返回布局 id
     */
    @Override
    protected int layoutId() {
        return R.layout.activity_sign;
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initUI() {
        super.initUI();

        // 设置深色状态栏
        AUtils.setDarkStatusBar(mActivity, true);
    }

    @Override
    protected void initData() {
        signInFragment = SignInFragment.newInstance();
        signUpFragment = SignUpFragment.newInstance();
        fragments = new AppFragment[]{signInFragment, signUpFragment};
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_fragment_container, signInFragment);
        ft.add(R.id.main_fragment_container, signUpFragment);
        ft.show(signInFragment);
        ft.hide(signUpFragment);
        ft.commit();
    }

    @Override
    public SignContract.ISignPresenter<SignContract.ISignView> createPresenter() {
        return new SignPresenterImpl();
    }

    /**
     * 账户注册
     */
    public void signUp(String account, String password) {
        showDialog(true);
        mPresenter.doSignUp(account, password);
    }

    /**
     * 注册完成
     */
    @Override
    public void onSignUpDone() {
        showDialog(false);
        switchFragment(0);
    }

    /**
     * 注册错误
     */
    @Override
    public void onSignUpError(int code, String msg) {
        showDialog(false);
        VMToast.make(mActivity, msg).error();
    }

    /**
     * 账户登录
     */
    public void signIn(String account, String password) {
        showDialog(true);
        mPresenter.doSignIn(account, password);
    }

    /**
     * 请求完成
     */
    @Override
    public void onSignInDone() {
        showDialog(false);
        ARouter.goMain(mActivity);
    }

    /**
     * 登录出错
     */
    @Override
    public void onSignInError(int code, String msg) {
        showDialog(false);
        VMToast.make(mActivity, msg).error();
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


    /**
     * 切换登录与注册
     */
    public void switchFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (index == 0) {
            ft.setCustomAnimations(R.animator.vm_top_in_animator, R.animator.vm_down_out_animator);
        } else {
            ft.setCustomAnimations(R.animator.vm_down_in_animator, R.animator.vm_top_out_animator);
        }
        if (!fragments[index].isAdded()) {
            ft.add(R.id.main_fragment_container, fragments[index]);
        }
        ft.hide(fragments[currIndex]);
        ft.show(fragments[index]);
        ft.commit();
        currIndex = index;
    }
}
