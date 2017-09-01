package com.x.x_core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.x.x_core.app.XCore;
import com.x.x_core.delegates.web.IPageLoadListener;
import com.x.x_core.delegates.web.WebDelegate;
import com.x.x_core.delegates.web.route.Router;
import com.x.x_core.ui.loader.XLoader;
import com.x.x_core.util.log.XLog;

/**
 * Created by 熊猿猿 on 2017/8/31/031.
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;

    private IPageLoadListener mIPageLoadListener = null;

    private static final Handler HANDLER = XCore.getHandler();

    public void setIPageLoadListener(IPageLoadListener mIPageLoadListener) {
        this.mIPageLoadListener = mIPageLoadListener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        XLog.d(url);
        //路由拦截，处理事件
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        XLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                XLoader.stopLoading();
            }
        }, 1000);
    }
}
