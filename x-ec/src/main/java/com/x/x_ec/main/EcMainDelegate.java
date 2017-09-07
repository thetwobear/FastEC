package com.x.x_ec.main;

import android.graphics.Color;

import com.x.x_core.delegates.base_main.BaseMainDelegate;
import com.x.x_core.delegates.base_main.BaseMainItemDelegate;
import com.x.x_core.delegates.base_main.TabBean;
import com.x.x_core.delegates.base_main.ItemBuilder;
import com.x.x_ec.main.discover.DiscoverDelegate;
import com.x.x_ec.main.home.HomeDelegate;
import com.x.x_ec.main.my.MyDelegate;
import com.x.x_ec.main.cart.ShopCartDelegate;
import com.x.x_ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class EcMainDelegate extends BaseMainDelegate {
    @Override
    public LinkedHashMap<TabBean, BaseMainItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<TabBean, BaseMainItemDelegate> items = new LinkedHashMap<>();
        items.put(new TabBean("{icon-home}", "主页"), new HomeDelegate());
        items.put(new TabBean("{icon-sort}", "分类"), new SortDelegate());
        items.put(new TabBean("{icon-find}", "发现"), new DiscoverDelegate());
        items.put(new TabBean("{icon-shopping-cart}", "购物车"), new ShopCartDelegate());
        items.put(new TabBean("{icon-my}", "我的"), new MyDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }

}
