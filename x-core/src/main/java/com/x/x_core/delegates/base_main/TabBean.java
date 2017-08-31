package com.x.x_core.delegates.base_main;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public final class TabBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public TabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
