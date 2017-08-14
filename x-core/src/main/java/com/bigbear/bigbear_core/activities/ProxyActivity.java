package com.bigbear.bigbear_core.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.bigbear.bigbear_core.R;
import com.bigbear.bigbear_core.delegates.BigBearDeleate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by 熊猿猿 on 2017/8/8/008.
 */

public abstract class ProxyActivity extends SupportActivity {

    public abstract BigBearDeleate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

}
