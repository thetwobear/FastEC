package com.x.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.x.x_core.activities.ProxyActivity;
import com.x.x_core.delegates.XDeleate;
import com.x.x_core.ui.launcher.ILauncherListener;
import com.x.x_core.ui.launcher.OnLauncherFinishTag;
import com.x.x_ec.launcher.LauncherDelegate;
import com.x.x_ec.sign.ISignListener;
import com.x.x_ec.sign.SignInDelegate;

public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
    }

    @Override
    public XDeleate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLauncherFinsih(OnLauncherFinishTag launcherTag) {
        switch (launcherTag) {
            case SIGNED:
                Toast.makeText(this, "用户登录了", Toast.LENGTH_SHORT).show();
                startWithPop(new ExampleDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "用户没登录", Toast.LENGTH_SHORT).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
