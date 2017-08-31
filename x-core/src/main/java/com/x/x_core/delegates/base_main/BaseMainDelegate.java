package com.x.x_core.delegates.base_main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.x.x_core.R;
import com.x.x_core.R2;
import com.x.x_core.delegates.XDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public abstract class BaseMainDelegate extends XDelegate implements View.OnClickListener {
    private final ArrayList<BaseMainItemDelegate> ITEM_DELEGATE = new ArrayList<>();
    private final ArrayList<TabBean> TAB_BEANS = new ArrayList<>();
    private final LinkedHashMap<TabBean, BaseMainItemDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickedColor = Color.RED;
    @BindView(R2.id.main_content_layout)
    ContentFrameLayout mainBarDeletage;
    @BindView(R2.id.main_bar_layout)
    LinearLayoutCompat mainBar;

    /**
     * 设置底部按钮、页面数据
     */
    public abstract LinkedHashMap<TabBean, BaseMainItemDelegate> setItems(ItemBuilder builder);

    /**
     * 设置初始选中的角标
     */
    public abstract int setIndexDelegate();

    /**
     * 设置选中的按钮字体、标题颜色
     */
    @ColorInt
    public abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.deletage_base_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过抽象方法从子类获取初次进入页面选中的页面
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        //添加/初始化页面
        final ItemBuilder itemBuilder = ItemBuilder.builder();
        final LinkedHashMap<TabBean, BaseMainItemDelegate> items = setItems(itemBuilder);
        ITEMS.putAll(items);

        final Set<Map.Entry<TabBean, BaseMainItemDelegate>> entries = ITEMS.entrySet();
        for (Map.Entry<TabBean, BaseMainItemDelegate> item : entries) {
            final TabBean key = item.getKey();//单个底部按钮bean
            final BaseMainItemDelegate value = item.getValue();//单个子页面
            TAB_BEANS.add(key);
            ITEM_DELEGATE.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            //给底部的按钮的父布局填充内容
            LayoutInflater.from(getContext()).inflate(R.layout.tab_item_icon_text_layout, mainBar);
            final RelativeLayout item = (RelativeLayout) mainBar.getChildAt(i);
            //给每个按钮设置TAG
            item.setTag(i);
            item.setOnClickListener(this);
            //图标
            final IconTextView icon = (IconTextView) item.getChildAt(0);
            //标题
            final AppCompatTextView title = (AppCompatTextView) item.getChildAt(1);
            //初始化数据
            final TabBean bean = TAB_BEANS.get(i);
            icon.setText(bean.getIcon());
            title.setText(bean.getTitle());
            if (i == mIndexDelegate) {
                title.setTextColor(mClickedColor);
                icon.setTextColor(mClickedColor);
            }
        }

        final SupportFragment[] pageArray = ITEM_DELEGATE.toArray(new SupportFragment[size]);
        //初始化页面：params1:用于填充页面的布局、params2：初始选中的角标、params3：页面数组
        loadMultipleRootFragment(R.id.main_content_layout, mIndexDelegate, pageArray);
    }

    @Override
    public void onClick(View view) {
        final int tag = (int) view.getTag();
        final int buttonCount = mainBar.getChildCount();
        for (int i = 0; i < buttonCount; i++) {
            final RelativeLayout buttonItem = (RelativeLayout) mainBar.getChildAt(i);
            final IconTextView icon = (IconTextView) buttonItem.getChildAt(0);
            final AppCompatTextView title = (AppCompatTextView) buttonItem.getChildAt(1);
            if (i == tag) {
                icon.setTextColor(mClickedColor);
                title.setTextColor(mClickedColor);
            } else {
                icon.setTextColor(Color.GRAY);
                title.setTextColor(Color.GRAY);
            }
        }
        //显示选中页面，隐藏上一个页面
        showHideFragment(ITEM_DELEGATE.get(tag), ITEM_DELEGATE.get(mCurrentDelegate));
        //重设选中角标
        mCurrentDelegate = tag;
    }

}
