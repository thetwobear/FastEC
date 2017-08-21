package com.x.x_core.wechat.template;

import com.x.x_core.wechat.BaseWXEntryActivity;
import com.x.x_core.wechat.XWeChat;

/**
 * Created by 熊猿猿 on 2017/8/16/016.
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        //登录成功后实际进入的Activity，直接finish()
        finish();
        //设置不需要动画
        overridePendingTransition(0, 0);
    }

    @Override
    protected void signInSuccess(String ueserInfo) {
        XWeChat.getInstance().getmSignInCallback().onSignInSuccess(ueserInfo);
    }
}
