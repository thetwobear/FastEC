package com.x.x_ec.main.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.x.x_core.delegates.base_main.BaseMainItemDelegate;
import com.x.x_ec.R;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class MyDelegate extends BaseMainItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_my;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
