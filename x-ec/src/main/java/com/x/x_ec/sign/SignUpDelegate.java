package com.x.x_ec.sign;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.x.x_core.delegates.XDeleate;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.util.log.XLog;
import com.x.x_ec.R;
import com.x.x_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊猿猿 on 2017/8/15/015.
 */

public class SignUpDelegate extends XDeleate {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText editSignUpName;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText editSignUpEmail;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText editSignUpPhone;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText editSignUpPassword;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText editSignUpRePassword;
    @BindView(R2.id.btn_sign_up)
    AppCompatButton btnSignUp;
    @BindView(R2.id.tv_link_sign_in)
    AppCompatTextView tvLinkSignIn;


    private ISignListener mISignListener=null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener= (ISignListener) activity;
        }
    }

    private boolean checkForm() {
        final String name = editSignUpName.getText().toString();
        final String email = editSignUpEmail.getText().toString();
        final String phone = editSignUpPhone.getText().toString();
        final String password = editSignUpPassword.getText().toString();
        final String rePassword = editSignUpRePassword.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            editSignUpName.setError("请输入姓名");
            isPass = false;
        } else {
            editSignUpName.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editSignUpEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            editSignUpEmail.setError(null);
        }
        if (phone.isEmpty() || phone.length() != 11) {
            editSignUpPhone.setError("手机号码错误");
            isPass = false;
        } else {
            editSignUpPhone.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            editSignUpPassword.setError("请输入至少6位数密码");
            isPass = false;
        } else {
            editSignUpPassword.setError(null);
        }
        if (rePassword.isEmpty() || rePassword.length() < 6 || !rePassword.equals(password)) {
            editSignUpRePassword.setError("两次输入的密码不一致");
            isPass = false;
        } else {
            editSignUpRePassword.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sing_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            RestClient.builder().url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
                    .params("name",editSignUpName.getText().toString())
                    .params("email",editSignUpEmail.getText().toString())
                    .params("phone",editSignUpPhone.getText().toString())
                    .params("password",editSignUpPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            XLog.d("USER_PROFILE",response);
                            SignHandler.onSignUp(response,mISignListener);
                        }
                    }).build().post();
//            Toast.makeText(getContext(), "验证成功", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickSignIn() {
        start(new SignInDelegate());
    }
}
