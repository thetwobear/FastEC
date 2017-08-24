package com.x.x_ec.main;

import android.graphics.Color;

import com.x.x_core.delegates.base_main.BaseMainDeletage;
import com.x.x_core.delegates.base_main.BaseMainItemDeletage;
import com.x.x_core.delegates.base_main.BottomTabBean;
import com.x.x_core.delegates.base_main.ItemBuilder;
import com.x.x_ec.main.compass.CompassDelegate;
import com.x.x_ec.main.home.HomeDelegate;
import com.x.x_ec.main.my.MyDelegate;
import com.x.x_ec.main.shopping_cart.ShoppingCartDelegate;
import com.x.x_ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class EcMainDelegate extends BaseMainDeletage {
    @Override
    public LinkedHashMap<BottomTabBean, BaseMainItemDeletage> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BaseMainItemDeletage> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new HomeDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new CompassDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShoppingCartDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new MyDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDeletage() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
