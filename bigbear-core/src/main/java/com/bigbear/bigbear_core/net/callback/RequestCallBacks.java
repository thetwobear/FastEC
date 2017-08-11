package com.bigbear.bigbear_core.net.callback;

import android.os.Handler;

import com.bigbear.bigbear_core.ui.BigBearLoader;
import com.bigbear.bigbear_core.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

public class RequestCallBacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();

    public RequestCallBacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (SUCCESS != null) {
                SUCCESS.onSuccess(response.body());
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
        stopLoading();
    }


    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        stopLoading();
    }

    private void stopLoading() {
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BigBearLoader.stopLoading();
                }
            }, 1000);
        }
    }

}
