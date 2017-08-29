package com.x.x_core.ui.banner;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.x.x_core.R;

import java.util.ArrayList;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 */

public class BannerCreater {

    public static void setDeault(ConvenientBanner<String> convenientBanner, ArrayList<String> banners, OnItemClickListener clickListener) {
        convenientBanner.setPages(new HolderCreater(), banners)
                .setPageIndicator(new int[]{R.drawable.doc_normal, R.drawable.doc_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }
}
