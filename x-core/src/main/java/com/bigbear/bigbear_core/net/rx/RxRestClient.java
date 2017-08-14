package com.bigbear.bigbear_core.net.rx;

import android.content.Context;

import com.bigbear.bigbear_core.net.CoutingRequestBoay;
import com.bigbear.bigbear_core.net.HttpMethod;
import com.bigbear.bigbear_core.net.RestCreator;
import com.bigbear.bigbear_core.net.callback.UploadProgressListener;
import com.bigbear.bigbear_core.ui.BigBearLoader;
import com.bigbear.bigbear_core.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

public class RxRestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final UploadProgressListener PROGRESS_LISTENER;
    private final RequestBody BODY;
    private final File FILE;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;


    public RxRestClient(String url,
                        WeakHashMap<String, Object> params,
                        UploadProgressListener uploadProgressListener,
                        RequestBody requestbody,
                        File file,
                        LoaderStyle loader_style,
                        Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.PROGRESS_LISTENER = uploadProgressListener;
        this.BODY = requestbody;
        this.FILE = file;
        this.LOADER_STYLE = loader_style;
        this.CONTEXT = context;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();

        if (LOADER_STYLE != null) {
            BigBearLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        Observable<String> observable = null;
        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAM:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAM:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
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
                observable = service.upload(URL, part);
                break;
            default:
                break;
        }
        return observable;
    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAM);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.PUT_RAM);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }

}
