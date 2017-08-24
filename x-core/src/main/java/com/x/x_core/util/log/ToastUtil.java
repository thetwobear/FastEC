package com.x.x_core.util.log;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by bear on 2016/5/9.
 */
public class ToastUtil {

    Context context;

    Toast mToast;

    /**
     * mToast.setGravity(0, 0, 0);  起点位置,水平向右位移,垂直向下位移 17,0,-30居中显示
     *
     * @param context
     */
    public ToastUtil(Context context) {
        this.context = context;
        this.mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
    }

    public void show(CharSequence s) {
        show(s, Toast.LENGTH_SHORT);
    }

    public void showCenter(CharSequence s){
        mToast.setText(s);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void show(CharSequence s, int duration) {
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToast.setText(s);
        mToast.show();
    }

    public void cancle() {
        mToast.cancel();
    }
}
