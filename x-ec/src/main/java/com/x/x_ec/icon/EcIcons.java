package com.x.x_ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by 熊猿猿 on 2017/8/7/007.
 */

public enum EcIcons implements Icon {

    icon_scan('\ue67e'),//扫码
    icon_ali_pay('\ue67c'),//支付宝
    icon_home('\ue64f'),//首页
    icon_sort('\ue652'),//分类
    icon_find('\ue644'),//发现
    icon_shopping_cart('\ue63f'),//购物车
    icon_my('\ue659'),//我
    icon_qr_code('\ue642'),//二维码
    icon_msg('\ue665'),//消息
    icon_select_all_false('\ue848'),//全选-空
    icon_select_all_true('\ue67f');//全选

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
