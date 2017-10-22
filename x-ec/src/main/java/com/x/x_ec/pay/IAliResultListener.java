package com.x.x_ec.pay;

/**
 * Created by 熊猿猿 on 2017/10/22/022.
 */

public interface IAliResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
