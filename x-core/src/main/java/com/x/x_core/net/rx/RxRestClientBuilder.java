package com.x.x_core.net.rx;

import android.content.Context;

import com.x.x_core.net.RestCreator;
import com.x.x_core.net.callback.UploadProgressListener;
import com.x.x_core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

public class RxRestClientBuilder {
    private String mUrl = null;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private UploadProgressListener mUploadProgressListener = null;
    private RequestBody mRequestbody = null;
    private File mFile = null;
    private LoaderStyle mLoader_style = null;
    private Context mContext = null;

    public RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object values) {
        PARAMS.put(key, values);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mRequestbody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    public final RxRestClientBuilder uploadProgress(UploadProgressListener uploadProgressListener) {
        this.mUploadProgressListener = uploadProgressListener;
        return this;
    }

    public final RxRestClientBuilder loader(LoaderStyle loaderStyle, Context context) {
        this.mLoader_style = loaderStyle;
        this.mContext = context;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mLoader_style = LoaderStyle.BallClipRotatePulseIndicator;
        this.mContext = context;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(
                mUrl,
                PARAMS,
                mUploadProgressListener,
                mRequestbody,
                mFile,
                mLoader_style,
                mContext);
    }
}
