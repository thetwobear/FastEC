package com.x.x_core.wechat.template;

import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.x.x_core.activities.ProxyActivity;
import com.x.x_core.delegates.XDelegate;
import com.x.x_core.wechat.BaseWXPayEntryActivity;

/**
 * Created by 熊猿猿 on 2017/8/16/016.
 */

public class WPayEntryTemplate extends BaseWXPayEntryActivity {

    @Override
    protected void onPaySuccess() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayFail() {
        Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}
