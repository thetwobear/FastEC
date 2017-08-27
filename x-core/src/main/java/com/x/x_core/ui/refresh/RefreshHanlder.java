package com.x.x_core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

import com.x.x_core.app.XCore;

/**
 * Created by 熊猿猿 on 2017/8/27/027.
 */

public class RefreshHanlder implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout SWIPE_REFRESH_LAYOUT;

    public RefreshHanlder(SwipeRefreshLayout swipeRefreshLayout) {
        this.SWIPE_REFRESH_LAYOUT = swipeRefreshLayout;
        SWIPE_REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    private void refresh() {
        SWIPE_REFRESH_LAYOUT.setRefreshing(true);
        XCore.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO: 2017/8/27/027 此处可以进行一些网络请求，请求结束后将下面代码复制进去
                SWIPE_REFRESH_LAYOUT.setRefreshing(false);
            }
        },1000);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
