package com.bigbear.bigbear_core.net;

import com.bigbear.bigbear_core.app.BigBear;
import com.bigbear.bigbear_core.app.ConfigType;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

public class RestCreator {


    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) BigBear.getConfigurations().get(ConfigType.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static final class OkhttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
