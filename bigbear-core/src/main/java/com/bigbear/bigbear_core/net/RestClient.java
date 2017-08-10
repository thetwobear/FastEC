package com.bigbear.bigbear_core.net;

import com.bigbear.bigbear_core.net.callback.IError;
import com.bigbear.bigbear_core.net.callback.IFailure;
import com.bigbear.bigbear_core.net.callback.IRequest;
import com.bigbear.bigbear_core.net.callback.ISuccess;
import com.bigbear.bigbear_core.net.callback.RequestCallBacks;
import com.bigbear.bigbear_core.net.callback.RxRequestCallBacks;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

@SuppressWarnings("ALL")
public class RestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody REQUESTBODY;

    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody requestbody) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.REQUESTBODY = requestbody;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        Call<String> call = null;
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getCallBack());
        }
    }

    private void rxRequest(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        Observable<Response<String>> observable = null;
        switch (method) {
            case GET:
                observable = service.rxGet(URL, PARAMS);
                break;
            case POST:
                observable = service.rxPost(URL, PARAMS);
                break;
            case PUT:
                observable = service.rxPut(URL, PARAMS);
                break;
            case DELETE:
                observable = service.rxDelete(URL, PARAMS);
                break;
            default:
                break;
        }
        if (observable != null) {
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver());
        }
    }

    private Callback<String> getCallBack() {
        return new RequestCallBacks(REQUEST, SUCCESS, FAILURE, ERROR);
    }

    private Observer<Response<String>> getObserver() {
        return new RxRequestCallBacks(REQUEST, SUCCESS, FAILURE, ERROR);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void rxGet() {
        rxRequest(HttpMethod.GET);
    }

    public final void rxPost() {
        rxRequest(HttpMethod.POST);
    }

    public final void rxPut() {
        rxRequest(HttpMethod.PUT);
    }

    public final void rxDelete() {
        rxRequest(HttpMethod.DELETE);
    }
}
