package com.bigbear.festec.example;

import android.app.Application;

import com.bigbear.bigbear_core.app.BigBear;
import com.bigbear.bigbear_core.net.interceptors.DebugInterceptor;
import com.bigbear.bigbear_ec.icon.FontEcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by bear on 2017/8/7/007.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BigBear.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/index/")
                .withLoaderDelayed(1000)
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
    }


}
