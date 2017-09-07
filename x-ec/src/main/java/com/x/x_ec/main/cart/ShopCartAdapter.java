package com.x.x_ec.main.cart;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.x.x_core.ui.recycler.MultiRecyclerAdapter;
import com.x.x_core.ui.recycler.MultiViewHolder;
import com.x.x_core.ui.recycler.MultipleFileds;
import com.x.x_core.ui.recycler.MultipleItemEntity;
import com.x.x_ec.R;

import java.util.List;

/**
 * Created by 熊猿猿 on 2017/9/4/004.
 */

public class ShopCartAdapter extends MultiRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    @Override
    protected void convert(MultiViewHolder helper, MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //取出所有值
                final int id = item.getFiled(MultipleFileds.ID);
                final String thumb = item.getFiled(MultipleFileds.IMAGE_URL);
                final String title = item.getFiled(ShopCartItemFields.TITLE);
                final String desc = item.getFiled(ShopCartItemFields.DESC);
                final int count = item.getFiled(ShopCartItemFields.COUNT);
                final double price = item.getFiled(ShopCartItemFields.PRICE);
                //获取所有控件
                final IconTextView checke = helper.getView(R.id.icon_item_shop_cart);
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

                break;
            default:
                break;
        }
    }
}
