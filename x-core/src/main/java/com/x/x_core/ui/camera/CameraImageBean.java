package com.x.x_core.ui.camera;

import android.net.Uri;

/**
 * Created by 熊猿猿 on 2017/10/22/022.
 */

public final class CameraImageBean {
    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance() {
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
