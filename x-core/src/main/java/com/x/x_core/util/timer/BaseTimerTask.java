package com.x.x_core.util.timer;

import java.util.TimerTask;

/**
 * Created by 熊猿猿 on 2017/8/14/014.
 */

public class BaseTimerTask extends TimerTask {
    private ITimerListener mITimerListener;

    public BaseTimerTask(ITimerListener iTimerListener) {
        this.mITimerListener = iTimerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
