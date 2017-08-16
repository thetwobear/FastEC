package com.x.x_ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x.x_core.app.AccountManger;
import com.x.x_ec.database.DatabaseManger;
import com.x.x_ec.database.UserProfile;

/**
 * Created by 熊猿猿 on 2017/8/15/015.
 */

public class SignHandler {
    public static void onSignIn(String response, ISignListener iSignListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");
        final UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManger.getInstance().getDao().insert(userProfile);
        //已经注册并登录成功
        AccountManger.setSignState(true);
        iSignListener.onSignInSuccess();
    }

    public static void onSignUp(String response, ISignListener iSignListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");
        final UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManger.getInstance().getDao().insert(userProfile);
        //已经注册并登录成功
        AccountManger.setSignState(true);
        iSignListener.onSignUpSuccess();
    }
}
