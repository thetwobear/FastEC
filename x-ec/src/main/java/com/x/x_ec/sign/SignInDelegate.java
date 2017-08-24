package com.x.x_ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.x.x_core.delegates.XDelegate;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.util.log.XLog;
import com.x.x_core.wechat.XWeChat;
import com.x.x_core.wechat.callbacks.IWeChatSignInCallback;
import com.x.x_ec.R;
import com.x.x_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊猿猿 on 2017/8/15/015.
 */

public class SignInDelegate extends XDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText editSignInEmail;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText editSignInPassword;
    @BindView(R2.id.btn_sign_in)
    AppCompatButton btnSignIn;
    @BindView(R2.id.tv_link_sign_up)
    AppCompatTextView tvLinkSignUp;
    @BindView(R2.id.itv_sign_in_weixin)
    IconTextView itvSignInWeixin;

    private ISignListener mISignListener = null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder().url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
                    .params("email", editSignInEmail.getText().toString())
                    .params("password", editSignInPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            XLog.d("USER_PROFILE", response);
                            SignHandler.onSignIn(response, mISignListener);
                        }
                    }).build().post();
//            Toast.makeText(getContext(), "验证成功", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R2.id.itv_sign_in_weixin)
    void onClickWeiChat() {
        XWeChat.getInstance().onSignInSuccess(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {

            }
        }).signIn();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLinkSignUp() {
        start(new SignUpDelegate());
    }


    private boolean checkForm() {

        final String email = editSignInEmail.getText().toString();
        final String password = editSignInPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editSignInEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            editSignInEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            editSignInPassword.setError("请输入至少6位数密码");
            isPass = false;
        } else {
            editSignInPassword.setError(null);
        }
        return isPass;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
