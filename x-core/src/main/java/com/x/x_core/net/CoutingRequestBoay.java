package com.x.x_core.net;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by bear on 2017/7/3/003.
 * 用于上传文件显示进度自定义的RequestBody
 */

public class CoutingRequestBoay extends RequestBody {

    /**
     * 相当于代理，内部的方法通过该对象调用
     */
    private RequestBody delegate;
    /**
     * 进度反馈回调接口
     */
    private ProgressListener listener;

    /**
     * 用于封装writeTo(BufferedSink sink)方法中的sink
     */
    private CountingSink countingSink;


    public CoutingRequestBoay(RequestBody delegate, ProgressListener listener) {
        this.delegate = delegate;
        this.listener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);//CountingSink包装成BufferedSink
        delegate.writeTo(bufferedSink);//实际调用的是RequestBoay的writeTo(BufferedSink sink)方法，CountingSink.write(Buffer source, long byteCount)
        bufferedSink.flush();
    }

    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }

    protected class CountingSink extends ForwardingSink {
        private long byteWritten;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten += byteCount;
            listener.onRequestProgress(byteWritten, contentLength());
        }
    }

    public interface ProgressListener {
        /**
         * @param byteWrited    已写的字节数
         * @param contentLength 总字节数
         */
        void onRequestProgress(long byteWrited, long contentLength);
    }
}
