package com.x.x_ec.main.sort.list;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.x.x_core.delegates.XDelegate;
import com.x.x_core.ui.recycler.ItemType;
import com.x.x_core.ui.recycler.MultiRecyclerAdapter;
import com.x.x_core.ui.recycler.MultiViewHolder;
import com.x.x_core.ui.recycler.MultipleFileds;
import com.x.x_core.ui.recycler.MultipleItemEntity;
import com.x.x_ec.R;
import com.x.x_ec.main.sort.SortDelegate;
import com.x.x_ec.main.sort.content.ContentDelegate;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 */

public class SortRecycleAdapter extends MultiRecyclerAdapter {

    private final SortDelegate SORT_DELEGATE;

    //上一个选中项
    private int mPrePosition = 0;

    protected SortRecycleAdapter(List<MultipleItemEntity> data, SortDelegate sortDelegate) {
        super(data);
        this.SORT_DELEGATE = sortDelegate;
        //添加布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_list_menu);
    }

    @Override
    protected void convert(final MultiViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final String name = item.getField(MultipleFileds.NAME);
                final boolean isClicked = item.getField(MultipleFileds.TAG);
                final AppCompatTextView nameTv = helper.getView(R.id.tv_vertical_item_name);
                final View line = helper.getView(R.id.view_line);
                final View itemView = helper.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mCurrentPosition = helper.getAdapterPosition();
                        if (mPrePosition != mCurrentPosition) {
                            //还原上一个选项
                            getData().get(mPrePosition).setFiled(MultipleFileds.TAG, false);
                            notifyItemChanged(mPrePosition);
                            //更新选中的项
                            item.setFiled(MultipleFileds.TAG, true);
                            notifyItemChanged(mCurrentPosition);
                            //替换选中角标
                            mPrePosition = mCurrentPosition;
                            final int contentId = item.getField(MultipleFileds.ID);
                            showContent(contentId);
                        }
                    }
                });
                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    nameTv.setTextSize(12);
//                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    nameTv.setTextSize(16);
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
//                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }
                nameTv.setText(name);

                break;
            default:
                break;
        }
    }

    private void showContent(int contentId) {
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate) {
        final XDelegate contentDelegate =
                SupportHelper.findFragment(SORT_DELEGATE.getChildFragmentManager(), ContentDelegate.class);
        if (contentDelegate != null) {
            //false 是否加入返回栈
            contentDelegate.getSupportDelegate().replaceFragment(delegate, false);
        }
    }
}
