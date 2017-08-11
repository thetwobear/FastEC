package com.bigbear.bigbear_core.net;

import android.content.Context;

import com.bigbear.bigbear_core.net.callback.IError;
import com.bigbear.bigbear_core.net.callback.IFailure;
import com.bigbear.bigbear_core.net.callback.IRequest;
import com.bigbear.bigbear_core.net.callback.ISuccess;
import com.bigbear.bigbear_core.ui.LoaderStyle;

import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

public class RestClientBuilder {
    private String mUrl = null;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IError mIError = null;
    private IFailure mIFailure = null;
    private RequestBody mRequestbody = null;
    private LoaderStyle mLoader_style = null;
    private Context mContext = null;

    public RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object values) {
        PARAMS.put(key, values);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mRequestbody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder loader(LoaderStyle loaderStyle, Context context) {
        this.mLoader_style = loaderStyle;
        this.mContext = context;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mLoader_style = LoaderStyle.BallClipRotatePulseIndicator;
        this.mContext = context;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mIRequest, mISuccess, mIFailure, mIError, mRequestbody, mLoader_style, mContext);
    }
}
