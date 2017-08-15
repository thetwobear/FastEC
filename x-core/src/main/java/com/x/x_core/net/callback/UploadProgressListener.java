package com.x.x_core.net.callback;

/**
 * Created by 熊猿猿 on 2017/8/11/011.
 */

public interface UploadProgressListener {
    void onProgress(long byteWrited, long contentLength);
}
