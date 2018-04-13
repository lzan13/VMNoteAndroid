package com.vmloft.develop.app.vnotes.sign;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vmloft.develop.app.vnotes.AppActivity;
import com.vmloft.develop.app.vnotes.R;
import com.vmloft.develop.app.vnotes.sign.presenter.ISignPresenter;
import com.vmloft.develop.app.vnotes.sign.presenter.SignInPresenter;
import com.vmloft.develop.app.vnotes.sign.view.ISignView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppActivity implements ISignView {

    private ISignView signView;
    private ISignPresenter signPresenter;

    @BindView(R.id.edit_account) EditText accountEdit;
    @BindView(R.id.edit_password) EditText passwordEdit;
    @BindView(R.id.btn_clear_account) ImageButton clearAccountBtn;
    @BindView(R.id.btn_show_password) ImageButton showPasswordBtn;
    @BindView(R.id.btn_sign_in) Button signInBtn;
    @BindView(R.id.layout_sign_progress) LinearLayout signProgress;

    private String account, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(activity);

        initView();
    }

    private void initView() {

        setSupportActionBar(getToolbar());
        getToolbar().setNavigationIcon(R.drawable.ic_close_black_24dp);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinish();
            }
        });

        accountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifyInputBox();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifyInputBox();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        signView = this;
        signPresenter = new SignInPresenter(signView);
    }

    @OnClick({R.id.btn_clear_account, R.id.btn_show_password, R.id.btn_sign_in})
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
        }
    }

    private void signIn() {
        account = accountEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();

        signInBtn.setEnabled(false);

        signPresenter.doSignIn(account, password);
    }

    /**
     * 控制密码是否可见
     */
    private void controlPassHide() {
        if (passwordEdit.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPasswordBtn.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        } else {
            passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPasswordBtn.setImageResource(R.drawable.ic_visibility_black_24dp);
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
    public void init(String email) {
        accountEdit.setText(email);
    }

    @Override
    public void onSignResult(boolean result, int code) {
        String str;
        if (result) {
            str = "Sign in success ";
        } else {
            str = "Sign in failed ";
        }
        Toast.makeText(activity, str + code, Toast.LENGTH_SHORT).show();

        signInBtn.setEnabled(true);
    }

    @Override
    public void onProgressState(boolean isShow) {
        if (isShow) {
            signProgress.setVisibility(View.VISIBLE);
        } else {
            signProgress.setVisibility(View.INVISIBLE);
        }
    }
}
