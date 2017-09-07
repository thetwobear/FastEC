package com.x.x_ec.main.cart;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.x.x_core.delegates.base_main.BaseMainItemDelegate;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.ui.recycler.MultipleItemEntity;
import com.x.x_core.util.dimen.DimenUtil;
import com.x.x_ec.R;
import com.x.x_ec.R2;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class ShopCartDelegate extends BaseMainItemDelegate implements ISuccess {

    @BindView(R2.id.tv_shop_cart_clear)
    AppCompatTextView tvShopCartClear;
    @BindView(R2.id.tv_shop_cart_delete)
    AppCompatTextView tvShopCartDelete;
    @BindView(R2.id.shop_cart_toolbar)
    Toolbar shopCartToolbar;
    @BindView(R2.id.rv_shop_cart_list)
    RecyclerView rvShopCartList;
    @BindView(R2.id.icon_tv_shop_cart_select_all)
    IconTextView iconTvShopCartSelectAll;
    @BindView(R2.id.llay_shop_cart_select_all)
    LinearLayoutCompat llayShopCartSelectAll;
    @BindView(R2.id.tv_shop_cart_total_amount)
    AppCompatTextView tvShopCartTotalAmount;
    @BindView(R2.id.tv_shop_cart_check_out)
    AppCompatTextView tvShopCartCheckOut;
    private ShopCartAdapter mAdapter = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shopCartToolbar.setPadding(0, DimenUtil.getStatusBarHeight(), 0, 0);
        }
        RestClient.builder()
                .url("shop_cart.php")
                .success(this)
                .loader(getContext())
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvShopCartList.setLayoutManager(manager);
        rvShopCartList.setAdapter(mAdapter);
    }
}
