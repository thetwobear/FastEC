package com.x.x_core.net.callback;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

public class RxRequestCallBacks implements io.reactivex.Observer<Response<String>> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public RxRequestCallBacks(IRequest request, ISuccess success, IFailure failure, IError error) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Response response) {
        if (response.isSuccessful()) {
            if (SUCCESS != null) {
                SUCCESS.onSuccess((String) response.body());
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

    }

    @Override
    public void onComplete() {
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
    }
}
