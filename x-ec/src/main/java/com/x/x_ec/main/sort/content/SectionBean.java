package com.x.x_ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by 熊猿猿 on 2017/8/30/030.
 */

public class SectionBean extends SectionEntity<SectionContentItemEntity> {
    //是否有更多选项
    private boolean mIsMore = false;

    private int mId = -1;

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public boolean isMore() {
        return mIsMore;
    }

    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
