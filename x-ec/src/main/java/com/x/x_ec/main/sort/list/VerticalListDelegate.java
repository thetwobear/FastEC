package com.x.x_ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.x.x_core.delegates.XDelegate;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.ui.recycler.MultipleItemEntity;
import com.x.x_ec.R;
import com.x.x_ec.R2;
import com.x.x_ec.main.sort.SortDelegate;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 */

public class VerticalListDelegate extends XDelegate {


    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView rvVerticalMenuList;


    private void initRecycleView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvVerticalMenuList.setLayoutManager(manager);
        //屏蔽动画效果
        rvVerticalMenuList.setItemAnimator(null);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecycleView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("sort_list.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<MultipleItemEntity> data =
                                new VerticalListDataConverter().setJsonData(response).convert();
                        final SortDelegate sortDelegate = getParentDelegate();
                        final SortRecycleAdapter sortRecycleAdapter = new SortRecycleAdapter(data, sortDelegate);
                        if (sortDelegate.isAdded())
                        try {
                            rvVerticalMenuList.setAdapter(sortRecycleAdapter);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .build()
                .get();
    }
}
