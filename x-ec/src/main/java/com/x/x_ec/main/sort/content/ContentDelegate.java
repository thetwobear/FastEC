package com.x.x_ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.x.x_core.delegates.XDelegate;
import com.x.x_core.net.RestClient;
import com.x.x_core.net.callback.ISuccess;
import com.x.x_core.util.log.XLog;
import com.x.x_ec.R;
import com.x.x_ec.R2;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 */

public class ContentDelegate extends XDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";

    private List<SectionBean> mData = null;

    @BindView(R2.id.rv_list_content)
    RecyclerView rvListContent;

    private int mContentId = -1;

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //spanCount 左右显示个数，瀑布流格式垂直
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvListContent.setLayoutManager(manager);
        initData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void initData() {
        RestClient.builder()
                .url("sort_content_list.php?contentId=" + mContentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mData = new SectionDataConverter().convert(response);
                        if (mData != null && mData.size() > 0) {
                            final SectionAdapter sectionAdapter = SectionAdapter.create(R.layout.item_section_content, R.layout.item_section_header, mData);
                            try {
                                rvListContent.setAdapter(sectionAdapter);
                            } catch (NullPointerException ignored) {
                                XLog.d(ignored.toString());
                            }
                        }
                    }
                })
                .build()
                .get();
    }

}
