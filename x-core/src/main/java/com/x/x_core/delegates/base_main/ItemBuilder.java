package com.x.x_core.delegates.base_main;

import java.util.LinkedHashMap;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public final class ItemBuilder {
    private final LinkedHashMap<TabBean, BaseMainItemDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(TabBean bean, BaseMainItemDelegate deletage) {
        ITEMS.put(bean, deletage);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<TabBean, BaseMainItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<TabBean, BaseMainItemDelegate> build() {
        return ITEMS;
    }
}
