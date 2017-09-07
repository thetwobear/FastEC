package com.x.x_core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.x.x_core.delegates.XDelegate;
import com.x.x_core.delegates.web.WebDelegate;
import com.x.x_core.delegates.web.WebDelegateImpl;

/**
 * Created by 熊猿猿 on 2017/8/31/031.
 */

public class Router {
    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(WebDelegate delegate, String url) {
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }

        final XDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.start(webDelegate);
        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null!");
        }
    }

    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    public final void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public final void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), delegate.getUrl());
    }

    public final void loadPage(WebDelegate delegate) {
        loadPage(delegate.getWebView(), delegate.getUrl());
    }


    private void callPhone(Context context, String url) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(url);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);

    }

}