package com.x.x_core.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by 熊猿猿 on 2017/8/27/027.
 */

public class MultipleItemEntity implements MultiItemEntity {
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MUTIPLE_FILEDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FILEDS_REFRENCE
            = new SoftReference<>(MUTIPLE_FILEDS, ITEM_QUEUE);


    public MultipleItemEntity(LinkedHashMap<Object, Object> fileds) {
        FILEDS_REFRENCE.get().putAll(fileds);
    }

    @Override
    public int getItemType() {
        return (int) FILEDS_REFRENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    @SuppressWarnings("uncheched")
    public final <T> T getField(Object key) {
        return (T) FILEDS_REFRENCE.get().get(key);
    }

    public final LinkedHashMap<?, ?> getFileds() {
        return FILEDS_REFRENCE.get();
    }

    public final MultiItemEntity setFiled(Object key, Object value) {
        FILEDS_REFRENCE.get().put(key, value);
        return this;
    }

    public static class Builder {

        private static final LinkedHashMap<Object, Object> FILEDS = new LinkedHashMap<>();

        public Builder() {
            FILEDS.clear();
        }

        public final Builder setItemType(int itemType) {
            FILEDS.put(MultipleFields.ITEM_TYPE, itemType);
            return this;
        }

        public final Builder setField(Object key, Object value) {
            FILEDS.put(key, value);
            return this;
        }

        public final Builder setFileds(LinkedHashMap<?, ?> fileds) {
            FILEDS.putAll(fileds);
            return this;
        }

        public final MultipleItemEntity build() {
            return new MultipleItemEntity(FILEDS);
        }
    }
}
