package com.x.x_core.wechat;

import android.app.Activity;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.x.x_core.app.ConfigKeys;
import com.x.x_core.app.XCore;

/**
 * Created by 熊猿猿 on 2017/8/16/016.
 */

public class XWeChat {
    static String APP_ID = XCore.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
    static String APP_SECRET = XCore.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;

    private static final class Holder {
        private static final XWeChat INSTANCE = new XWeChat();
    }

    public static XWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private XWeChat() {
        final Activity activity = XCore.getConfiguration(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getIwxapi() {
        return WXAPI;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);

    }
}
