package com.x.x_core.delegates.base_main;

import android.view.KeyEvent;
import android.view.View;

import com.x.x_core.R;
import com.x.x_core.delegates.XDelegate;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public abstract class BaseMainItemDelegate extends XDelegate implements View.OnKeyListener {
    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null) {
            //此处不设置将导致key监听失效
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > EXIT_TIME) {
                getProxyActivity().getToastUtil().show("双击退出" + getString(R.string.app_name));
                mExitTime = System.currentTimeMillis();
            } else {
                _mActivity.finish();
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
            }
            return true;
        }
        return false;
    }
}
