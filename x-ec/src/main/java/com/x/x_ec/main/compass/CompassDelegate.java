package com.x.x_ec.main.compass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.x.x_core.delegates.base_main.BaseMainItemDeletage;
import com.x.x_ec.R;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class CompassDelegate extends BaseMainItemDeletage {
    @Override
    public Object setLayout() {
        return R.layout.delegate_compass;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
