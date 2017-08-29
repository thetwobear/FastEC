package com.x.x_core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by 熊猿猿 on 2017/8/28/028.
 */

public class MultiViewHolder extends BaseViewHolder {
    private MultiViewHolder(View view) {
        super(view);
    }

    public static MultiViewHolder create(View view) {
        return new MultiViewHolder(view);
    }
}
