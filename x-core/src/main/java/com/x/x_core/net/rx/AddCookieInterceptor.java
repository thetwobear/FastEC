package com.x.x_core.net.rx;

import com.x.x_core.util.storage.XPreference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 熊猿猿 on 2017/9/3/003.
 */

public final class AddCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        Observable
                .just(XPreference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        //给原生API请求附带WebView拦截下来的Cookie
                        builder.addHeader("Cookie", cookie);
                    }
                });
        return chain.proceed(builder.build());
    }
}
