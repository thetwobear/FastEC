package com.bigbear.bigbear_core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by bear on 2017/8/7/007.
 */

public class Configurator {
    private static final HashMap<Object, Object> BIGBEAR_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    public Configurator() {
        BIGBEAR_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    final HashMap<Object, Object> getConfigs() {
        return BIGBEAR_CONFIGS;
    }

    /**
     * 线程安全的懒汉模式
     *
     * @return
     */
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 设置配置结束
     */
    public final void configure() {
        initIcons();
        BIGBEAR_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    public final Configurator withApiHost(String host) {
        BIGBEAR_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withLoaderDelayed(long delayed) {
        BIGBEAR_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    public Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        BIGBEAR_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        BIGBEAR_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 配置检查
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) BIGBEAR_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = BIGBEAR_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) BIGBEAR_CONFIGS.get(key);
    }

}
