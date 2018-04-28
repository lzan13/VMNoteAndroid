package com.vmloft.develop.app.vmnote.sign;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.app.base.AppFragment;
import com.vmloft.develop.app.vmnote.bean.Account;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzan13 on 2018/4/16.
 * 登录 UI 界面
 */
public class SignInFragment extends AppFragment {

    @BindView(R.id.edit_account) EditText accountEdit;
    @BindView(R.id.edit_password) EditText passwordEdit;
    @BindView(R.id.btn_clear_account) ImageButton clearAccountBtn;
    @BindView(R.id.btn_show_password) ImageButton showPasswordBtn;
    @BindView(R.id.btn_sign_in) Button signInBtn;

    private String account, password;

    private FragmentListener listener;

    /**
     * 创建实例对象的工厂方法
     */
    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 初始化 Fragment 界面 layout_id
     *
     * @return 返回布局 id
     */
    @Override
    protected int initLayoutId() {
        return R.layout.fragment_sign_in;
    }

    /**
     * 初始化界面控件，将 Fragment 变量和 View 建立起映射关系
     */
    @Override
    protected void initView() {
        ButterKnife.bind(this, getView());
        accountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                verifyInputBox();
            }
        });
        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                verifyInputBox();
            }
        });
    }

    /**
     * 加载数据
     */
    @Override
    protected void initData() {
        account = SPManager.getInstance().getAccountName();
        accountEdit.setText(account);
    }

    @OnClick({R.id.btn_clear_account, R.id.btn_show_password, R.id.btn_sign_in,
                     R.id.btn_sign_up_go})
    void onClick(View view) {
        switch (view.getId()) {
        case R.id.btn_clear_account:
            accountEdit.setText("");
            break;
        case R.id.btn_show_password:
            controlPassHide();
            break;
        case R.id.btn_sign_in:
            signIn();
            break;
        case R.id.btn_sign_up_go:
            listener.onAction(R.id.btn_sign_up_go, null);
            break;
        }
    }

    /**
     * 登录
     */
    private void signIn() {
        account = accountEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();
        Account entity = new Account(account, password);
        listener.onAction(R.id.btn_sign_in, entity);
    }

    /**
     * 控制密码是否可见
     */
    private void controlPassHide() {
        if (passwordEdit.getTransformationMethod()
                .equals(PasswordTransformationMethod.getInstance())) {
            passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPasswordBtn.setImageResource(R.drawable.ic_visibility);
        } else {
            passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPasswordBtn.setImageResource(R.drawable.ic_visibility_off);
        }
    }

    /**
     * 校验输入框
     */
    private void verifyInputBox() {
        account = accountEdit.getText().toString().toLowerCase().trim();
        password = passwordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            clearAccountBtn.setVisibility(View.INVISIBLE);
        } else {
            clearAccountBtn.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(account)) {
            signInBtn.setEnabled(false);
            signInBtn.setAlpha(0.5f);
        } else {
            signInBtn.setEnabled(true);
            signInBtn.setAlpha(1.0f);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FragmentListener) context;
    }
}
