package com.bigbear.bigbear_core.net;

import android.content.Context;

import com.bigbear.bigbear_core.net.callback.IError;
import com.bigbear.bigbear_core.net.callback.IFailure;
import com.bigbear.bigbear_core.net.callback.IRequest;
import com.bigbear.bigbear_core.net.callback.ISuccess;
import com.bigbear.bigbear_core.net.callback.RequestCallBacks;
import com.bigbear.bigbear_core.net.callback.RxRequestCallBacks;
import com.bigbear.bigbear_core.net.callback.UploadProgressListener;
import com.bigbear.bigbear_core.net.download.DownloadHandler;
import com.bigbear.bigbear_core.ui.BigBearLoader;
import com.bigbear.bigbear_core.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private final UploadProgressListener PROGRESS_LISTENER;
    private final RequestBody BODY;
    private final File FILE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;


    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      UploadProgressListener uploadProgressListener,
                      RequestBody requestbody,
                      File file,
                      String downloadDir,
                      String extension,
                      String name,
                      LoaderStyle loader_style,
                      Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.PROGRESS_LISTENER = uploadProgressListener;
        this.BODY = requestbody;
        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.LOADER_STYLE = loader_style;
        this.CONTEXT = context;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            BigBearLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        Call<String> call = null;
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAM:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAM:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                //增加上传进度显示
                final CoutingRequestBoay coutingRequestBoay = new CoutingRequestBoay(requestBody, new CoutingRequestBoay.ProgressListener() {
                    @Override
                    public void onRequestProgress(long byteWrited, long contentLength) {
                        if (PROGRESS_LISTENER != null) {
                            PROGRESS_LISTENER.onProgress(byteWrited, contentLength);
                        }
                    }
                });
                final MultipartBody.Part part = MultipartBody.Part.createFormData("file", FILE.getName(), coutingRequestBoay);
                call = RestCreator.getRestService().upload(URL, part);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getCallBack());
        }
    }

    /**
     * RxJava request
     *
     * @param method
     */
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
            case POST_RAM:

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
        return new RequestCallBacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    private Observer<Response<String>> getObserver() {
        return new RxRequestCallBacks(REQUEST, SUCCESS, FAILURE, ERROR);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAM);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAM);
        }
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, SUCCESS, FAILURE, ERROR, DOWNLOAD_DIR, EXTENSION, NAME).handleDownload();
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

    public final void rxDownload() {
        new DownloadHandler(URL, REQUEST, SUCCESS, FAILURE, ERROR, DOWNLOAD_DIR, EXTENSION, NAME).rxHandleDownload();
    }
}
