package com.x.x_ec.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.ui.recycler.MultipleRecyclerAdapter;
import com.x.x_core.ui.recycler.MultipleViewHolder;
import com.x.x_core.ui.recycler.MultipleFields;
import com.x.x_core.ui.recycler.MultipleItemEntity;
import com.x.x_ec.R;

import java.util.List;

/**
 * Created by 熊猿猿 on 2017/9/4/004.
 */

public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private boolean isSelectedAll = false;

    private ICartItemListener iCartItemListener;

    private double mTotalPrice = 0.00;

    public void setiCartItemListener(ICartItemListener iCartItemListener) {
        this.iCartItemListener = iCartItemListener;
    }

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化总价
        for (MultipleItemEntity entity : data) {
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count;
            mTotalPrice = mTotalPrice + total;
        }
        //添加购物车Item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        List<MultipleItemEntity> datas = getData();
        final int size = datas.size();
        for (int i = 0; i < size; i++) {
            final MultipleItemEntity entity = datas.get(i);
            entity.setFiled(ShopCartItemFields.IS_SELECTED, isSelectedAll);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(final MultipleViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //取出所有值
                final int id = item.getField(MultipleFields.ID);
                final String thumb = item.getField(MultipleFields.IMAGE_URL);
                final String title = item.getField(ShopCartItemFields.TITLE);
                final String desc = item.getField(ShopCartItemFields.DESC);
                final int count = item.getField(ShopCartItemFields.COUNT);
                final double price = item.getField(ShopCartItemFields.PRICE);
                final boolean isSelected = item.getField(ShopCartItemFields.IS_SELECTED);
                //获取所有控件
                final IconTextView iconSelect = helper.getView(R.id.icon_item_shop_cart);
                final AppCompatImageView imgThumb = helper.getView(R.id.img_item_shop_cart);
                final AppCompatTextView tvTitle = helper.getView(R.id.item_shop_cart_title);
                final AppCompatTextView tvDesc = helper.getView(R.id.item_shop_cart_desc);
                final AppCompatTextView tvPrice = helper.getView(R.id.item_shop_cart_price);
                final IconTextView iconMinus = helper.getView(R.id.icon_item_shop_cart_minus);
                final IconTextView iconPlus = helper.getView(R.id.icon_item_shop_cart_plus);
                final AppCompatTextView tvCount = helper.getView(R.id.tv_item_shop_cart_count);
                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);

                //通过状态值设置显示左边勾选状态
                if (isSelected) {
                    iconSelect.setText(R.string.icon_selected_true);
                    iconSelect.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                } else {
                    iconSelect.setText(R.string.icon_selected_false);
                    iconSelect.setTextColor(Color.GRAY);
                }
                //设置点击事件
                iconSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final boolean currentSelected = item.getField(ShopCartItemFields.IS_SELECTED);
                        if (currentSelected) {
                            iconSelect.setText(R.string.icon_selected_false);
                            iconSelect.setTextColor(Color.GRAY);
                            item.setFiled(ShopCartItemFields.IS_SELECTED, false);
                        } else {
                            iconSelect.setText(R.string.icon_selected_true);
                            iconSelect.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                            item.setFiled(ShopCartItemFields.IS_SELECTED, true);
                        }
                    }
                });
                //增减事件
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = item.getField(ShopCartItemFields.COUNT);
                        if (Integer.parseInt(tvCount.getText().toString()) > 1) {
                            RestClient.builder()
                                    .loader(mContext)
                                    .url("shop_cart_count.php")
                                    .params("count", currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum--;
                                            tvCount.setText(String.valueOf(countNum));
                                            if (iCartItemListener != null) {
                                                mTotalPrice = mTotalPrice - price;
                                                final double itemTotalPrice = countNum * price;
                                                iCartItemListener.onItemClick(itemTotalPrice);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        } else if (Integer.parseInt(tvCount.getText().toString()) == 1) {
                            Toast.makeText(mContext, "删除购物车商品", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = item.getField(ShopCartItemFields.COUNT);
                        if (Integer.parseInt(tvCount.getText().toString()) >= 1) {
                            RestClient.builder()
                                    .loader(mContext)
                                    .url("shop_cart_count.php")
                                    .params("count", currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum++;
                                            tvCount.setText(String.valueOf(countNum));
                                            if (iCartItemListener != null) {
                                                mTotalPrice = mTotalPrice + price;
                                                final double itemTotalPrice = countNum * price;
                                                iCartItemListener.onItemClick(itemTotalPrice);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
