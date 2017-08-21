package com.x.x_core.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by 熊猿猿 on 2017/8/16/016.
 */

public abstract class BaseWXActivity extends AppCompatActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //必须写在onCreate中
        XWeChat.getInstance().getIwxapi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //此重载方法中再写一次，保障各型号手机适配的问题
        setIntent(intent);
        XWeChat.getInstance().getIwxapi().handleIntent(getIntent(), this);
    }
}
