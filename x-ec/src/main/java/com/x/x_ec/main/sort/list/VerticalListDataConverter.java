package com.x.x_ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.x.x_core.ui.recycler.DataConverter;
import com.x.x_core.ui.recycler.ItemType;
import com.x.x_core.ui.recycler.MultipleFileds;
import com.x.x_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 */

public class VerticalListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> verticalList = new ArrayList<>();
        final JSONArray jsonArray = JSON.parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");
        final int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject itemObject = jsonArray.getJSONObject(i);
            final int id = itemObject.getInteger("id");
            final String name = itemObject.getString("name");
            final MultipleItemEntity entity = new MultipleItemEntity.Builder()
                    .setFiled(MultipleFileds.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setFiled(MultipleFileds.ID, id)
                    .setFiled(MultipleFileds.NAME, name)
                    .setFiled(MultipleFileds.TAG, false)
                    .build();
            verticalList.add(entity);
            //设置被选中的项
            verticalList.get(0).setFiled(MultipleFileds.TAG, true);
        }
        return verticalList;
    }
}
