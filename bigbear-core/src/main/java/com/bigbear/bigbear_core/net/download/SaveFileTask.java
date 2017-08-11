package com.bigbear.bigbear_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.bigbear.bigbear_core.app.BigBear;
import com.bigbear.bigbear_core.net.callback.IRequest;
import com.bigbear.bigbear_core.net.callback.ISuccess;
import com.bigbear.bigbear_core.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 熊猿猿 on 2017/8/11/011.
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;

    public SaveFileTask(IRequest iRequest, ISuccess iSuccess) {
        this.IREQUEST = iRequest;
        this.ISUCCESS = iSuccess;
    }


    @Override
    protected File doInBackground(Object... paramses) {
        String name = (String) paramses[0];
        String downloadDir = (String) paramses[1];
        String extension = (String) paramses[2];
        final ResponseBody body = (ResponseBody) paramses[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "bigbear_dowmloads";
        }
        //此处根据情况自由扩展
        if (extension == null || extension.equals("")) {
            extension = "";
        }

        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (ISUCCESS != null) {
            ISUCCESS.onSuccess(file.getPath());
        }
        if (IREQUEST != null) {
            IREQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    /**
     * 识别apk文件自动安装
     *
     * @param apkFile 下载的文件
     */
    private void autoInstallApk(File apkFile) {
        if (FileUtil.getExtension(apkFile.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//新起一个栈，可能在后台执行
            install.setAction(Intent.ACTION_VIEW);
            //Android version>6.0+ 判断
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = FileProvider.getUriForFile(BigBear.getApplicationContext(), BigBear.getApplicationContext().getPackageName() + ".provider", apkFile);
                install.setDataAndType(uri, "application/vnd.android.package-archive");
            } else {
                install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            BigBear.getApplicationContext().startActivity(install);
        }
    }
}
