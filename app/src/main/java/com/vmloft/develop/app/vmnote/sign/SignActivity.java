package com.vmloft.develop.app.vmnote.sign;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.base.AppFragment;
import com.vmloft.develop.app.vmnote.app.base.AppMVPActivity;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.common.router.NavRouter;
import com.vmloft.develop.app.vmnote.sign.presenter.SignPresenterImpl;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignView;
import com.vmloft.develop.app.vmnote.sign.SignContract.ISignPresenter;
import com.vmloft.develop.library.tools.VMFragment;
import com.vmloft.develop.library.tools.widget.VMToast;

import butterknife.BindView;

/**
 * 登录界面
 */
public class SignActivity extends AppMVPActivity<ISignView, ISignPresenter<ISignView>> implements ISignView, VMFragment.FragmentListener {

    @BindView(R.id.layout_sign_progress) LinearLayout progressView;

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
    protected int initLayoutId() {
        return R.layout.activity_sign;
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        signInFragment = SignInFragment.newInstance();
        signUpFragment = SignUpFragment.newInstance();
        fragments = new AppFragment[] { signInFragment, signUpFragment };
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, signInFragment);
        ft.add(R.id.fragment_container, signUpFragment);
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
    private void signUp(Account entity) {
        showDialog(true);
        presenter.doSignUp(entity);
    }

    /**
     * 注册完成
     */
    @Override
    public void onSignUpDone() {
        showDialog(false);
        VMToast.make("Sign up success!").showDone();
        switchFragment(0);
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
        presenter.doSignIn(entity);
    }

    /**
     * 请求完成
     */
    @Override
    public void onSignInDone() {
        showDialog(false);
        NavRouter.goMain(activity);
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (action) {
        case R.id.btn_sign_in:
            signIn((Account) obj);
            break;
        case R.id.btn_go_sign_in:
            switchFragment(0);
            break;
        case R.id.btn_sign_up:
            signUp((Account) obj);
            break;
        case R.id.btn_go_sign_up:
            switchFragment(1);
            break;
        }
    }

    private void switchFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (index == 0) {
            ft.setCustomAnimations(R.animator.vm_top_in_animator, R.animator.vm_down_out_animator);
        } else {
            ft.setCustomAnimations(R.animator.vm_down_in_animator, R.animator.vm_top_out_animator);
        }
        if (!fragments[index].isAdded()) {
            ft.add(R.id.fragment_container, fragments[index]);
        }
        ft.hide(fragments[currIndex]);
        ft.show(fragments[index]);
        ft.commit();
        currIndex = index;
    }
}
