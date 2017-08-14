package com.bigbear.bigbear_ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by 熊猿猿 on 2017/8/7/007.
 */

public enum EcIcons implements Icon {

    icon_scan('\ue678'),
    icon_ali_pay('\ue67c');

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
