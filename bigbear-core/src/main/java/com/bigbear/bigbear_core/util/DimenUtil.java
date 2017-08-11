package com.bigbear.bigbear_core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.bigbear.bigbear_core.app.BigBear;

/**
 * Created by 熊猿猿 on 2017/8/10/010.
 */

public class DimenUtil {

    public static int getSrceenWidth() {
        final Resources resources = BigBear.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getSrceenHeight() {
        final Resources resources = BigBear.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
