package com.x.x_core.app;

import android.app.Activity;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by bear on 2017/8/7/007.
 */

public class Configurator {
    private static final HashMap<Object, Object> MY_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();


    final HashMap<Object, Object> getConfigs() {
        return MY_CONFIGS;
    }

    /**
     * 线程安全的懒汉模式
     *
     * @return 返回当前对象
     */
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 设置配置开始-对应配置未完成
     */
    private Configurator() {
        MY_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    /**
     * 设置配置结束-对应配置完成
     */
    public final void configure() {
        initIcons();
        MY_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    /**
     * @param host 配置全局的ApiHost
     * @return 返回当前对象
     */
    public final Configurator withApiHost(String host) {
        MY_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    /**
     * 初始化字体图标库，或者说添加
     */
    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withLoaderDelayed(long delayed) {
        MY_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        MY_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }


    public final Configurator withWeChatAppSecret(String appSecret) {
        MY_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        MY_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    /**
     * 配置字体图标库
     *
     * @param descriptor 可以配置FontAwesomeModule，或自定义类继承IconFontDescriptor实现的字体图标库
     */
    public Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    /**
     * @param interceptor 配置网络拦截器
     */
    public Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        MY_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * @param interceptors 配置网络拦截器
     */
    @SuppressWarnings("unused")
    public Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        MY_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 配置检查
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) MY_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    /**
     * 传入对应的key值，返回出对应的配置信息
     *
     * @param key key
     * @param <T> value 泛型
     * @return value
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = MY_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " is null");
        }
        return (T) MY_CONFIGS.get(key);
    }

}
