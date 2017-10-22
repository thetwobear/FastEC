package com.x.x_core.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.x.x_core.delegates.web.event.Event;
import com.x.x_core.delegates.web.event.EventManager;
import com.x.x_core.util.log.LogUtil;

/**
 * Created by 熊猿猿 on 2017/8/31/031.
 */

final class JsInterface {
    private final WebDelegate DELEGATE;

    private JsInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static JsInterface create(WebDelegate delegate) {
        return new JsInterface(delegate);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params) {
//        LogUtil.e(params);
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
