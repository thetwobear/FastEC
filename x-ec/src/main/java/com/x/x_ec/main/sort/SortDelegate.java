package com.x.x_ec.main.sort;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.x.x_core.delegates.base_main.BaseMainItemDeletage;
import com.x.x_core.util.dimen.DimenUtil;
import com.x.x_ec.R;
import com.x.x_ec.R2;
import com.x.x_ec.main.sort.content.ContentDelegate;
import com.x.x_ec.main.sort.list.VerticalListDelegate;

import butterknife.BindView;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class SortDelegate extends BaseMainItemDeletage {

    @BindView(R2.id.tb_sort)
    Toolbar tbSort;
    @BindView(R2.id.vertical_list_container)
    ContentFrameLayout verticalListContainer;
    @BindView(R2.id.sort_content_container)
    ContentFrameLayout sortContentContainer;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initToolBar();
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listDelegate);
        //设置右侧第一个分类显示，默认显示分类一
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));
    }

    private void initToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tbSort.setPadding(0, DimenUtil.getStatusBarHeight(), 0, 0);
        }
    }

}
