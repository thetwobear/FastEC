package com.x.x_core.ui.camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.x.x_core.R;
import com.x.x_core.delegates.PermissionCheckerDelegate;
import com.x.x_core.util.file.FileUtil;

import java.io.File;

/**
 * Created by 熊猿猿 on 2017/10/22/022.
 */

public class CameraHandler implements View.OnClickListener {
    private final AlertDialog mDialog;
    private final PermissionCheckerDelegate mDelegate;

    public CameraHandler(PermissionCheckerDelegate mDelegate) {
        this.mDelegate = mDelegate;
        this.mDialog = new AlertDialog.Builder(mDelegate.getContext()).create();
    }

    final void beginCameraDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.dimAmount = 0.5f;
            window.setAttributes(params);
            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.photodialog_btn_cancel) {
            mDialog.cancel();
        } else if (id == R.id.photodialog_btn_native) {
            pickPhoto();
            mDialog.cancel();
        } else if (id == R.id.photodialog_btn_take) {
            takePhoto();
            mDialog.cancel();
        }
    }

    private String getPhotoName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    private void takePhoto() {
        final String currentPhotoName = getPhotoName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhotoName);
        //兼容android7.0+
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
//            final Uri realUri = mDelegate.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//            final Uri uri = mDelegate.getContext().getContentResolver()
//                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//            需要将Uri转化为实际路径
//            final File realFile = FileUtils.getFileByPath(FileUtil.getRealFilePath(mDelegate.getContext(), uri));

//            final Uri realUri = Uri.fromFile(realFile);
            final Uri realUri = FileProvider.getUriForFile(mDelegate.getContext(), mDelegate.getContext().getPackageName() + ".provider", tempFile);
            CameraImageBean.getInstance().setPath(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri);
        } else {
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        mDelegate.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }

    private void pickPhoto() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        mDelegate.startActivityForResult
                (Intent.createChooser(intent, "选择获取图片的方式"), RequestCodes.PICK_PHOTO);
    }
}
