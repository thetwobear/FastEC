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

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by 熊猿猿 on 2017/8/24/024.
 */

public abstract class BaseMainDeletage extends XDelegate implements View.OnClickListener {
    private final ArrayList<BaseMainItemDeletage> ITEM_DELEGATE = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BaseMainItemDeletage> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickedColor = Color.RED;
    @BindView(R2.id.bottom_bar_deletage)
    ContentFrameLayout bottomBarDeletage;
    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat bottomBar;

    public abstract LinkedHashMap<BottomTabBean, BaseMainItemDeletage> setItems(ItemBuilder builder);

    public abstract int setIndexDeletage();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.deletage_base_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDeletage();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        final ItemBuilder itemBuilder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BaseMainItemDeletage> items = setItems(itemBuilder);
        ITEMS.putAll(items);
//        Set<Map.Entry<BottomTabBean, BaseMainItemDeletage>> entries = ITEMS.entrySet();
        for (Map.Entry<BottomTabBean, BaseMainItemDeletage> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BaseMainItemDeletage value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATE.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.tab_item_icon_text_layout, bottomBar);
            final RelativeLayout item = (RelativeLayout) bottomBar.getChildAt(i);
            item.setTag(i);
            item.setOnClickListener(this);
            //找到对应icon、title
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            //初始化数据
            final BottomTabBean bean = TAB_BEANS.get(i);
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i == mIndexDelegate) {
                itemTitle.setTextColor(mClickedColor);
                itemIcon.setTextColor(mClickedColor);
            }
        }

        final SupportFragment[] delegateArray = ITEM_DELEGATE.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_deletage, mIndexDelegate, delegateArray);
    }

    private void reSetColor() {
        final int count = bottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) bottomBar.getChildAt(i);
            final IconTextView icon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView titile = (AppCompatTextView) item.getChildAt(1);
            icon.setTextColor(Color.GRAY);
            titile.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View view) {
        final int tag = (int) view.getTag();
        reSetColor();
        final RelativeLayout item = (RelativeLayout) bottomBar.getChildAt(tag);
        final IconTextView icon = (IconTextView) item.getChildAt(0);
        final AppCompatTextView titile = (AppCompatTextView) item.getChildAt(1);
        icon.setTextColor(mClickedColor);
        titile.setTextColor(mClickedColor);
        //注意先手顺序
        showHideFragment(ITEM_DELEGATE.get(tag), ITEM_DELEGATE.get(mCurrentDelegate));
        mCurrentDelegate = tag;
    }
}
