package com.x.x_ec.main.home;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.x.x_core.ui.recycler.RgbValue;
import com.x.x_ec.R;

/**
 * Created by 熊猿猿 on 2017/8/29/029.
 * 当前没用到
 */
@SuppressWarnings("unused")
public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    //顶部距离
    private int mDistanceY = 0;
    //变化速率
    private static final int SPEED = 3;
    //定义变化颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255, 124, 2);

    /**
     * 必须要有两个参数的构造方法
     *
     * @param context
     * @param attrs
     */
    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

   /* @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        *//**
         * 相当于R.id.rv_home 的控件该Behavior被接管
         *//*
        return dependency.getId() == R.id.rv_home;
    }*/

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //增加滑动距离
        mDistanceY += dy;
        //toolbar的高度
        final int targetHeight = child.getBottom();
        //当滑动时，并且距离小于 toolbar 高度的时候，调整渐变色
        if (mDistanceY > 0 && mDistanceY <= targetHeight) {
            final float scale = (float) mDistanceY / targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        } else if (mDistanceY > targetHeight) {
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        }


//        if (dy <= 0) {   //设置标题的背景颜色
//            child.setBackgroundColor(Color.argb(0, 0, 0, 0));
//        } else if (dy > 0 && dy <= targetHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
//            float scale = (float) dy / targetHeight;
//            float alpha = 255 * scale;
//            child.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
//        } else if (dy > targetHeight) {    //滑动到banner下面设置普通颜色
//            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
//        }
    }
}
