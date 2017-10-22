package com.x.x_ec.main.cart;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.x.x_core.delegates.base_main.BaseMainItemDelegate;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.ui.recycler.MultipleItemEntity;
import com.x.x_core.util.dimen.DimenUtil;
import com.x.x_core.util.log.XLog;
import com.x.x_ec.R;
import com.x.x_ec.R2;
import com.x.x_ec.pay.FastPay;
import com.x.x_ec.pay.IAliResultListener;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class ShopCartDelegate extends BaseMainItemDelegate implements ISuccess, ICartItemListener,IAliResultListener {

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
    @BindView(R2.id.tv_shop_cart_clear)
    AppCompatTextView tvShopCartClear;
    @BindView(R2.id.tv_shop_cart_delete)
    AppCompatTextView tvShopCartDelete;
    @BindView(R2.id.view_stub_shop_cart)
    ViewStubCompat viewStubShopCart;


    private ShopCartAdapter mAdapter = null;
    /**
     * 记录当前购物车选中的数量
     */
    private int mCurrentCount = 0;

    /**
     * 购物车商品总数量
     */
    private int mTotalCount = 0;

    private double mTotalPrice = 0.00;

    @OnClick(R2.id.llay_shop_cart_select_all)
    void onClickSelectAll() {
        final boolean tag = (boolean) iconTvShopCartSelectAll.getTag();
        if (!tag) {
            iconTvShopCartSelectAll.setText(R.string.icon_selected_true);
            iconTvShopCartSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            iconTvShopCartSelectAll.setTag(true);
            //更新RecyclerView 显示
            mAdapter.setIsSelectedAll(true);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            iconTvShopCartSelectAll.setText(R.string.icon_selected_false);
            iconTvShopCartSelectAll.setTextColor(Color.GRAY);
            iconTvShopCartSelectAll.setTag(false);
            //更新RecyclerView 显示
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }


    @OnClick(R2.id.tv_shop_cart_delete)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> datas = mAdapter.getData();
        if (datas.isEmpty()) return;
        final int size = datas.size();
        final List<MultipleItemEntity> removeDatas = new ArrayList<>();
        for (MultipleItemEntity entity : datas) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                removeDatas.add(entity);
            }
        }

        for (MultipleItemEntity entity : removeDatas) {
            int removePosition;
            final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
            if (entityPosition > mCurrentCount - 1) {
                removePosition = entityPosition - (mTotalCount - mCurrentCount);
            } else {
                removePosition = entityPosition;
            }
            if (removePosition <= mAdapter.getItemCount()) {
                mAdapter.remove(removePosition);
                mCurrentCount = mAdapter.getItemCount();
                //更新数据
                mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());
            }
        }
        checkedItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_check_out)
    void onClickPay() {
        FastPay.create(this).beginPayDialog();
    }

    /**
     * 创建订单,和支付是没有关系的
     */
    private void createOrder() {
        final String orderUrl = "http://app.api.zanzuanshi.com/api/v1/peyment";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        orderParams.put("userId", 246392);
        orderParams.put("amount", 0.01);
        orderParams.put("comment", "测试支付");
        orderParams.put("type", 1);
        orderParams.put("ordertype", 0);
        orderParams.put("isannonymous", true);
        orderParams.put("followeduser", 0);
        RestClient.builder()
                .url(orderUrl)
                .params(orderParams)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                        XLog.d("ORDER", response);
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();

                    }
                })
                .build()
                .post();

    }

    private void checkedItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            final View stubView = viewStubShopCart.inflate();
            final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "去购物吧", Toast.LENGTH_SHORT).show();
                }
            });
            rvShopCartList.setVisibility(View.GONE);
        } else {
            rvShopCartList.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R2.id.tv_shop_cart_clear)
    void onClickRemoveAll() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkedItemCount();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        iconTvShopCartSelectAll.setTag(false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shopCartToolbar.setPadding(0, DimenUtil.getStatusBarHeight(), 0, 0);
        }

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
        mAdapter.setiCartItemListener(this);
        mTotalPrice = mAdapter.getTotalPrice();
        checkedItemCount();
    }

    @Override
    public void onResume() {
        super.onResume();

        RestClient.builder()
                .url("shop_cart.php")
                .success(this)
                .loader(getContext())
                .build()
                .get();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        XLog.d(itemTotalPrice);
        final double price = mAdapter.getTotalPrice();
        tvShopCartTotalAmount.setText(String.valueOf(price));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
