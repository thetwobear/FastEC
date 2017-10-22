package com.x.x_ec.main.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.x.x_core.delegates.base_main.BaseMainItemDelegate;
import com.x.x_ec.R;
import com.x.x_ec.R2;
import com.x.x_ec.main.my.list.ListAdapter;
import com.x.x_ec.main.my.list.ListBean;
import com.x.x_ec.main.my.list.ListItemType;
import com.x.x_ec.main.my.order.OrderListDelegate;
import com.x.x_ec.main.my.profile.UserProfileDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class MyDelegate extends BaseMainItemDelegate {
    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings = null;
    public static final String ORDER_TYPE = "ORDER_TYPE";

    @Override
    public Object setLayout() {
        return R.layout.delegate_my;
    }

    private Bundle mArgs = null;

    private void startOrderListByType() {
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder() {
        mArgs.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
//                .setDelegate(new AddressDelegate())
                .setText("收货地址")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
//                .setDelegate(new SettingsDelegate())
                .setText("系统设置")
                .build();
        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);
        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
//        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
}
