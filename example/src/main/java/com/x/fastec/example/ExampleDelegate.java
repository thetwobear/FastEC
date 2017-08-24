package com.x.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.x.x_core.delegates.XDelegate;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.IError;
import com.x.x_core.net.callback.IFailure;
import com.x.x_core.net.callback.IRequest;
import com.x.x_core.net.callback.ISuccess;

/**
 * Created by 熊猿猿 on 2017/8/9/009.
 */

public class ExampleDelegate extends XDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        test();
    }

    private void test() {
        RestClient.builder()
                .url("http://127.0.0.1/index/")
                .loader(getContext())
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {
                        System.out.println("开始请求");
                    }

                    @Override
                    public void onRequestEnd() {
                        System.out.println("请求结束");
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("", response);
//                        Toast.makeText(BigBear.getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        System.out.println("请求失败");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        System.out.println(msg);
                    }
                })
                .build()
                .rxGet();
    }
}
