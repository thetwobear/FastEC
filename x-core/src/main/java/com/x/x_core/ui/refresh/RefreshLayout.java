package com.x.x_core.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by bear on 2017/5/31/031.
 * 自定义下拉刷新，可以防止首页轮播图左右滑动误触发下拉刷新
 */

public class RefreshLayout extends SwipeRefreshLayout {
    private int mTouchSlop;

    // 上一次触摸时的X坐标
    private float mPrevX;
    // 上一次触摸时的Y坐标
    private float mPrevY;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                mPrevY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                final float eventY = event.getY();
                //移动结束两点距离444
                double c = Math.sqrt(Math.pow((eventX - mPrevX), 2) + Math.pow((eventY - mPrevY), 2));
                //直角边长度
                double a = Math.abs(eventY - mPrevY);
                //得到余弦值
                double cosine = a / c;
                //对余弦值处理
                //参考http://blog.csdn.net/mrlixirong/article/details/9610651
                if (cosine < -1.0) {
                    cosine = -1.0;
                } else if (cosine > 1.0) {
                    cosine = 1.0;
                }
                //反余弦求得角度
                double acos = Math.acos(cosine) * 100;
                //角度大于15度时不执行刷新
                if (acos > 15) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(event);
    }
}
