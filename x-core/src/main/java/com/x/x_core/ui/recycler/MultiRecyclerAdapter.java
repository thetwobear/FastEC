package com.x.x_core.ui.recycler;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.x.x_core.R;
import com.x.x_core.ui.banner.BannerCreater;
import com.x.x_core.util.dimen.DimenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 熊猿猿 on 2017/8/28/028.
 */

public class MultiRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultiViewHolder> implements
        BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {


    /**
     * 确保初始化一次banner，防止重复item加载
     */
    private boolean isInitBanner = false;

    /**
     * 图片加载策略
     */
    private static final RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    protected MultiRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultiRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultiRecyclerAdapter(data);
    }

    public static MultiRecyclerAdapter create(DataConverter converter) {
        return new MultiRecyclerAdapter(converter.convert());
    }

    @Override
    protected void convert(MultiViewHolder helper, MultipleItemEntity item) {
        final String text;
        final String imageUrl;
        final ArrayList<String> banners;
        //单图片设置图片宽高占屏幕一半
        final LinearLayoutCompat.LayoutParams imgParams1 = new LinearLayoutCompat.LayoutParams(DimenUtil.getSrceenWidth() / 2, DimenUtil.getSrceenWidth() / 2);
        switch (helper.getItemViewType()) {
            case ItemType.TEXT:
                text = item.getField(MultipleFileds.TEXT);
                helper.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                imageUrl = item.getField(MultipleFileds.IMAGE_URL);
                AppCompatImageView singleImg = helper.getView(R.id.img_single);
                final int spanSize = item.getField(MultipleFileds.SPAN_SIZE);
                if (spanSize == 2) {
                    singleImg.setLayoutParams(imgParams1);
                }
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(REQUEST_OPTIONS)
                        .into(singleImg);

                break;
            case ItemType.TEXT_IMAGE:
                text = item.getField(MultipleFileds.TEXT);
                imageUrl = item.getField(MultipleFileds.IMAGE_URL);
                helper.setText(R.id.text_multiple, text);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(REQUEST_OPTIONS)
                        .into((AppCompatImageView) helper.getView(R.id.img_multiple));
                break;
            case ItemType.BANNER:
                if (!isInitBanner) {
                    banners = item.getField(MultipleFileds.BANNERS);
                    final ConvenientBanner<String> convenientBanner = helper.getView(R.id.banner_recycle_item);
                    BannerCreater.setDeault(convenientBanner, banners, this);
                    isInitBanner = true;
                }
                break;

        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFileds.SPAN_SIZE);
    }

    @Override
    protected MultiViewHolder createBaseViewHolder(View view) {
        //返回自定义的MultiViewHolder
        return MultiViewHolder.create(view);
    }

    private void init() {
        //设置不同item，不同布局
        addItemType(ItemType.TEXT, R.layout.item_multi_single_text);
        addItemType(ItemType.IMAGE, R.layout.item_multi_single_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multi_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multi_banner);

        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);

    }

    @Override
    public void onItemClick(int position) {

    }
}
