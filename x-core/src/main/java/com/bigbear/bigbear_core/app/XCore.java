package com.bigbear.bigbear_core.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by bear on 2017/8/7/007.
 */

public final class XCore {

    /**
     * @return 返回保存有配置信息的对象
     */
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static Configurator init(Context context) {
        getConfigurator().getConfigs().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return getConfigurator();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfigurator().getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }
}
