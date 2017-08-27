package com.x.x_core.ui.recycler;

import java.util.ArrayList;

/**
 * Created by 熊猿猿 on 2017/8/27/027.
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new RuntimeException("Data is null");
        }
        return mJsonData;
    }
}
