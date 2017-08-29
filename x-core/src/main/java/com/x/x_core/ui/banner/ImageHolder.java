package com.x.x_core.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 */

public class ImageHolder implements Holder<String> {

    private AppCompatImageView mImageView = null;
    private static final RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        final LinearLayoutCompat.LayoutParams params =
                new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(params);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(context)
                .load(data)
                .apply(REQUEST_OPTIONS)
                .into(mImageView);
    }
}
