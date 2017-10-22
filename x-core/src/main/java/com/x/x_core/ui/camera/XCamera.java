package com.x.x_core.ui.camera;

import android.net.Uri;

import com.x.x_core.delegates.PermissionCheckerDelegate;
import com.x.x_core.util.file.FileUtil;

/**
 * Created by 熊猿猿 on 2017/10/22/022.
 */

public class XCamera {

    public static Uri createCropFile() {
        return Uri.parse(FileUtil.createFile("crop_image",FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
