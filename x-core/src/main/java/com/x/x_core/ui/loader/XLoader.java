package com.x.x_core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.x.x_core.R;
import com.x.x_core.util.dimen.DimenUtil;

import java.util.ArrayList;

/**
 * Created by 熊猿猿 on 2017/8/10/010.
 */
@SuppressWarnings("unused")
public class XLoader {

    /**
     * 缩放比例
     */
    private static final int LOADER_SIZE_SCALE = 8;
    /**
     * 上下偏移比例
     */
    private static final int LOADER_OFFSET_SCALE = 10;

    /**
     * 统一管理
     */
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    /**
     * 默认loader
     */
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();


    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);
        int deviceWidth = DimenUtil.getSrceenWidth();
        int deviceHeight = DimenUtil.getSrceenWidth();
        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
           final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context, Enum<LoaderStyle> styleEnum) {
        showLoading(context, styleEnum.name());
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }
}
