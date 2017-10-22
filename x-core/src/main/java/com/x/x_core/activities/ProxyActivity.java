package com.x.x_core.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.x.x_core.R;
import com.x.x_core.delegates.XDelegate;
import com.x.x_core.util.dimen.DimenUtil;
import com.x.x_core.util.log.ToastUtil;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by 熊猿猿 on 2017/8/8/008.
 */

public abstract class ProxyActivity extends SupportActivity {

    public abstract XDelegate setRootDelegate();

    private ToastUtil toastUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //手动设置沉浸式状态栏
//        DimenUtil.translucentStatusBar(this);

        initContainer(savedInstanceState);

        //通过依赖包方式设置沉浸式状态栏
//        StatusBarCompat.translucentStatusBar(this, true);


    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
        toastUtil = new ToastUtil(this);
    }

    public ToastUtil getToastUtil() {
        return toastUtil;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

}
