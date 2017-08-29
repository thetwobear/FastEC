package com.x.x_ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 熊猿猿 on 2017/8/30/030.
 */

public class SectionDataConverter {
    final List<SectionBean> convert(String json) {
        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("section");
            //添加titile
            final SectionBean sectionBean = new SectionBean(true, title);
            sectionBean.setId(id);
            sectionBean.setIsMore(true);
            dataList.add(sectionBean);

            //商品内容
            final JSONArray goods = data.getJSONArray("goods");
            final int goodsSize = goods.size();
            for (int j = 0; j < goodsSize; j++) {
                //单个商品内容
                final JSONObject contentItem = goods.getJSONObject(j);
                final int goodsId = contentItem.getInteger("goods_id");
                final String goodsName = contentItem.getString("goods_name");
                final String goodsThumb = contentItem.getString("goods_thumb");
                //内容包装
                final SectionContentItemEntity contentEntity = new SectionContentItemEntity();
                contentEntity.setGoodsId(goodsId);
                contentEntity.setGoodsName(goodsName);
                contentEntity.setGoodsThumb(goodsThumb);
                //添加内容
                dataList.add(new SectionBean(contentEntity));
            }
        }
        return dataList;
    }
}
