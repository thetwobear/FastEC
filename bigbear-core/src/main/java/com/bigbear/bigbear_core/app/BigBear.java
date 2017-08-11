package com.bigbear.bigbear_core.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by bear on 2017/8/7/007.
 */

public final class BigBear {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return (Context) Configurator.getInstance().getConfigs().get(ConfigKeys.APPLICATION_CONTEXT);
    }
}
