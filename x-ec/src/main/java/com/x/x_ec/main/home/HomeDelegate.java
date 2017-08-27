package com.x.x_ec.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.x.x_core.delegates.base_main.BaseMainItemDeletage;
import com.x.x_core.ui.refresh.RefreshHanlder;
import com.x.x_ec.R;
import com.x.x_ec.R2;

import butterknife.BindView;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class HomeDelegate extends BaseMainItemDeletage {
    @BindView(R2.id.rv_home)
    RecyclerView rvHome = null;
    @BindView(R2.id.srl_home)
    SwipeRefreshLayout srlHome = null;
    @BindView(R2.id.icon_home_scan)
    IconTextView iconHomeScan = null;
    @BindView(R2.id.et_home_search)
    AppCompatEditText etHomeSearch = null;
    @BindView(R2.id.icon_home_message)
    IconTextView iconHomeMessage = null;
    @BindView(R2.id.tb_home)
    Toolbar tbHome = null;
    private RefreshHanlder mRefreshHanlder = null;


    private void initRefreshLayout() {
        srlHome.setColorSchemeResources(
                android.R.color.holo_orange_light
        );
        //下拉刷新小球起始高度，终止高度
        srlHome.setProgressViewOffset(true, tbHome.getHeight(), 300);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHanlder = new RefreshHanlder(srlHome);
    }
}
