package com.bigbear.bigbear_core.app;

import android.support.v4.app.INotificationSideChannel;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bear on 2017/8/7/007.
 */

public class Configurator {
    private static final HashMap<String, Object> CONFIGS = new HashMap<>();

    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    public Configurator() {
        CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    final HashMap<String, Object> getConfigs() {
        return CONFIGS;
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
        CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    public final Configurator withApiHost(String host) {
        CONFIGS.put(ConfigType.API_HOST.name(), host);
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

    public Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    /**
     * 配置检查
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) CONFIGS.get(key.name());
    }

}
