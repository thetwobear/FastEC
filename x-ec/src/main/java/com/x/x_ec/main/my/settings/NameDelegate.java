package com.x.x_ec.main.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.x.x_core.delegates.XDelegate;
import com.x.x_ec.R;

/**
 * Created by 熊猿猿 on 2017/10/22/022.
 */

public class NameDelegate extends XDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
