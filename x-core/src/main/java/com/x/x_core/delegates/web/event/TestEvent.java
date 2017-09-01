package com.x.x_core.delegates.web.event;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by 熊猿猿 on 2017/8/31/031.
 */

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_LONG).show();
        if (getAction().equals("test")) {
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall()",null);
                }
            });
        }
        return null;
    }
}
