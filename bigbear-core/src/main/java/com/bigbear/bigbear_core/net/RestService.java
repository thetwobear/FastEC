package com.bigbear.bigbear_core.net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

public interface RestService {

    @GET("login")
    Call<String> get(@QueryMap Map<String, Object> params);

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    @Streaming//边下载边写入，防止内存溢出
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);


    //RxJava api

    @GET
    Observable<Response<String>> rxGet(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Observable<Response<String>> rxPost(@Url String url, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT
    Observable<Response<String>> rxPut(@Url String url, @FieldMap Map<String, Object> params);

    @DELETE
    Observable<Response<String>> rxDelete(@Url String url, @QueryMap Map<String, Object> params);

    @Streaming//边下载边写入，防止内存溢出
    @GET
    Observable<ResponseBody> rxDownload(@Url String url, @QueryMap Map<String, Object> params);

    @Multipart
    @POST
    Observable<Response<String>> rxUpload(@Url String url, @Part MultipartBody.Part file);
}
