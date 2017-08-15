package com.x.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.x.x_core.activities.ProxyActivity;
import com.x.x_core.delegates.XDeleate;
import com.x.x_ec.sign.SignInDelegate;
import com.x.x_ec.sign.SignUpDelegate;

public class ExampleActivity extends ProxyActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
    }

    @Override
    public XDeleate setRootDelegate() {
        return new SignInDelegate();
    }

}
