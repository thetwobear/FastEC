package com.x.x_core.app;

import com.x.x_core.util.storage.XPreference;

/**
 * Created by 熊猿猿 on 2017/8/16/016.
 */

public class AccountManger {
    private enum SignTag {
        SIGN_TAG
    }

    /**
     * 保存用户登录状态，登录后调用
     *
     * @param state 状态值
     */
    public static void setSignState(boolean state) {
        XPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    /**
     * 判断用户是否已登录
     *
     * @return 返回值true 登录
     */
    private static boolean isSignIn() {
        return XPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker iUserChecker) {
        if (isSignIn()) {
            iUserChecker.onSignIn();
        } else {
            iUserChecker.onNotSignIn();
        }
    }
}
