package com.bigbear.bigbear_core.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by bear on 2017/8/7/007.
 */

public final class BigBear {

    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getConfigs();
    }

    public static Context getApplication(){
        return (Context) Configurator.getInstance().getConfigs().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
