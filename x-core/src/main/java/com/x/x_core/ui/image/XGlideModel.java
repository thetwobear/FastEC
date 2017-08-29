package com.x.x_core.ui.image;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 */
@GlideModule
public class XGlideModel extends AppGlideModule {
  /*
  这个类用于生成GlideApp，通过以下方式加载图片（旧的链式调用）
    GlideApp.with(mContext)
            .load("url")
            .into();
            */
}
