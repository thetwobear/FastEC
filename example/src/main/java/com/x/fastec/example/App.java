package com.x.fastec.example;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.x.x_core.app.XCore;
import com.x.x_core.delegates.web.event.TestEvent;
import com.x.x_ec.database.DatabaseManger;
import com.x.x_ec.icon.FontEcModule;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by bear on 2017/8/7/007.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XCore.init(this)
                .withIcon(new FontAwesomeModule())//FontAwesome字体图标库
                .withIcon(new FontEcModule())//添加自定义字体
                .withApiHost("http://116.196.95.67/RestServer/api/")
                .withLoaderDelayed(1000)
                .withJavascriptInterface("latte")
//                .withWebEvent("test", new TestEvent())
                .withWeChatAppId("")//微信AppId
                .withWeChatAppSecret("")//微信AppSecret
//                .withInterceptor(new DebugInterceptor("index", R.raw.test))//自定义网络拦截器
                .withInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//拦截body内容并打印
                .configure();
        DatabaseManger.getInstance().init(this);
//        initStetho();
    }

    //FaceBook Chrome 调试初始化
    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }


}
