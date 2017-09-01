package com.x.x_core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.x.x_core.app.XCore;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.ui.recycler.DataConverter;
import com.x.x_core.ui.recycler.MultiRecyclerAdapter;

/**
 * Created by 熊猿猿 on 2017/8/27/027.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private final RefreshLayout SWIPE_REFRESH_LAYOUT;

    private final PagingBean PAGING_BEAN;
    private final RecyclerView RECYCLE_VIEW;
    private MultiRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    private RefreshHandler(RefreshLayout swipeRefreshLayout,
                           RecyclerView recyclerView,
                           DataConverter converter,
                           PagingBean bean
    ) {
        this.RECYCLE_VIEW = recyclerView;
        this.CONVERTER = converter;
        this.PAGING_BEAN = bean;
        this.SWIPE_REFRESH_LAYOUT = swipeRefreshLayout;
        SWIPE_REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(RefreshLayout swipeRefreshLayout, RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean());
    }

    private void refresh() {
        SWIPE_REFRESH_LAYOUT.setRefreshing(true);
        XCore.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 此处可以进行一些网络请求，请求结束后将下面代码复制进去
                SWIPE_REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 1000);
    }

    public void firstPage(final String url) {
        PAGING_BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        PAGING_BEAN
                                .setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        /**
                         * CONVERTER=HonmeDataConverter
                         * CONVERTER设置数据，进行转换
                         * MultiRecyclerAdapter.create()传入数据转换器，转换数据
                         */
                        mAdapter = MultiRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLE_VIEW);
                        RECYCLE_VIEW.setAdapter(mAdapter);
                        PAGING_BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
