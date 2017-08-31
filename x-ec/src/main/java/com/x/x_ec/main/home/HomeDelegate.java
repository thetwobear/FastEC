package com.x.x_ec.main.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.x.x_core.delegates.base_main.BaseMainItemDelegate;
import com.x.x_core.ui.recycler.BaseDecoration;
import com.x.x_core.ui.recycler.RgbValue;
import com.x.x_core.ui.refresh.RefreshHanlder;
import com.x.x_core.ui.refresh.RefreshLayout;
import com.x.x_core.util.dimen.DimenUtil;
import com.x.x_ec.R;
import com.x.x_ec.R2;
import com.x.x_ec.main.EcMainDelegate;

import butterknife.BindView;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public class HomeDelegate extends BaseMainItemDelegate {
    @BindView(R2.id.rv_home)
    RecyclerView rvHome;
    @BindView(R2.id.srl_home)
    RefreshLayout srlHome;
    @BindView(R2.id.icon_home_scan)
    IconTextView iconHomeScan;
    @BindView(R2.id.et_home_search)
    AppCompatEditText etHomeSearch;
    @BindView(R2.id.icon_home_message)
    IconTextView iconHomeMessage;
    @BindView(R2.id.tb_home)
    Toolbar tbHome;
    private RefreshHanlder mRefreshHanlder = null;


    private void initRefreshLayout() {
        srlHome.setColorSchemeResources(
                android.R.color.holo_orange_light
        );
        //下拉刷新小球起始高度，终止高度
        srlHome.setProgressViewOffset(true, 120, 300);
    }

    int mDistanceY = 0;
    //定义变化颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255, 124, 2);

    private void initRecycleView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        rvHome.setLayoutManager(manager);
        //设置分割线
        rvHome.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(), android.R.color.white), DimenUtil.dip2px(1)));

        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //增加滑动距离
                mDistanceY += dy;
                //toolbar的高度
                final int targetHeight = tbHome.getBottom();
                //当滑动时，并且距离小于 toolbar 高度的时候，调整渐变色
                if (mDistanceY > 0 && mDistanceY <= targetHeight) {
                    final float scale = (float) mDistanceY / targetHeight;
                    final float alpha = scale * 255;
                    tbHome.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
                } else if (mDistanceY > targetHeight) {
                    tbHome.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
                }
            }
        });

        final EcMainDelegate mainDelegate = getParentDelegate();
        rvHome.addOnItemTouchListener(HomeItemClickListener.create(mainDelegate));
    }

    private void initToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tbHome.setPadding(0, DimenUtil.getStatusBarHeight(), 0, 0);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initToolBar();
        initRefreshLayout();
        initRecycleView();
        mRefreshHanlder.firstPage("index.php");

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHanlder = RefreshHanlder.create(srlHome, rvHome, new HomeDataConverter());
    }

}
