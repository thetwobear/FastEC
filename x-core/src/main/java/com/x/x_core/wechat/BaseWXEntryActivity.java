package com.x.x_core.wechat;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.IError;
import com.x.x_core.net.callback.IFailure;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.util.log.XLog;

/**
 * Created by 熊猿猿 on 2017/8/16/016.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    /**
     * 用户登录成功后的回调
     *
     * @param ueserInfo 获取到的用户信息
     */

    protected abstract void signInSuccess(String ueserInfo);

    /**
     * 微信发送请求到第三方应用后的回调
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 第三方应用发送请求到微信后的回调
     *
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(XWeChat.APP_ID)
                .append("&secret=")
                .append(XWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        XLog.d("authUrl", authUrl.toString());
        getAuth(authUrl.toString());
    }

    private void getAuth(String authUrl) {
        RestClient.builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObject = JSONObject.parseObject(response);
                        final String accessToken = authObject.getString("access_token");
                        final String openId = authObject.getString("openId");
                        final StringBuffer uesrInfoUrl = new StringBuffer();
                        uesrInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openId")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");
                        XLog.d("uesrInfoUrl", uesrInfoUrl.toString());
                        getUserInfo(uesrInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient.builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        signInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
