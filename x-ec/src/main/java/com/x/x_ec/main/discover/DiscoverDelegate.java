package com.x.x_ec.main.discover;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.x.x_core.delegates.base_main.BaseMainItemDelegate;
import com.x.x_core.delegates.web.WebDelegateImpl;
import com.x.x_core.util.dimen.DimenUtil;
import com.x.x_ec.R;
import com.x.x_ec.R2;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class DiscoverDelegate extends BaseMainItemDelegate {

    @BindView(R2.id.common_toolbar_title)
    TextView commonToolbarTitle;
    @BindView(R2.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R2.id.web_discovery_container)
    ContentFrameLayout webDiscoveryContainer;

    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        commonToolbarTitle.setText("发现");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            commonToolbar.setPadding(0, DimenUtil.getStatusBarHeight(), 0, 0);
        }

        final WebDelegateImpl webDelegate = WebDelegateImpl.create("index.html");
        WebView view = webDelegate.getWebView();
//        final WebDelegateImpl webDelegate = WebDelegateImpl.create("http://m.liankur.com/");
//        final WebDelegateImpl webDelegate = WebDelegateImpl.create("http://192.168.1.116:8031/");
        webDelegate.setTopDelegate(this.getParentDelegate());
        loadRootFragment(R.id.web_discovery_container, webDelegate);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
