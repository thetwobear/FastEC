package com.x.x_ec.main.home;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.x.x_core.delegates.XDelegate;
import com.x.x_ec.main.detail.GoodsDetailDelegate;

import retrofit2.http.DELETE;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 */

public class HomeItemClickListener extends SimpleClickListener {
    private final XDelegate DELEGATE;

    private HomeItemClickListener(XDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static HomeItemClickListener create(XDelegate delegate) {
        return new HomeItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final GoodsDetailDelegate detailDelegate = GoodsDetailDelegate.create();
        DELEGATE.start(detailDelegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
