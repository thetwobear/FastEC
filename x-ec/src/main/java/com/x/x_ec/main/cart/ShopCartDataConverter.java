package com.x.x_ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.x.x_core.ui.recycler.DataConverter;
import com.x.x_core.ui.recycler.MultipleFields;
import com.x.x_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by 熊猿猿 on 2017/9/4/004.
 */

public class ShopCartDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray jsonArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            final String title = data.getString("title");
            final String desc = data.getString("desc");
            final double price = data.getDouble("price");
            final int id = data.getInteger("id");
            final int count = data.getInteger("count");
            final String thumb = data.getString("thumb");
            final MultipleItemEntity entity = new MultipleItemEntity.Builder()
                    .setField(MultipleFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(ShopCartItemFields.TITLE, title)
                    .setField(ShopCartItemFields.DESC, desc)
                    .setField(ShopCartItemFields.COUNT, count)
                    .setField(ShopCartItemFields.PRICE, price)
                    .setField(ShopCartItemFields.IS_SELECTED, false)
                    .setField(ShopCartItemFields.POSITION, i)
                    .build();
            dataList.add(entity);
        }
        return dataList;
    }
}
