package com.x.x_ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.x.x_core.delegates.XDeleate;
import com.x.x_core.util.storage.XPreference;
import com.x.x_core.util.timer.BaseTimerTask;
import com.x.x_core.util.timer.ITimerListener;
import com.x.x_ec.R;
import com.x.x_ec.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by 熊猿猿 on 2017/8/14/014.
 */

public class LauncherDelegate extends XDeleate implements ITimerListener {

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView tvLauncherTimer;

    private int mCount = 5;
    private Timer mTimer = null;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickLauncherTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    /**
     * 判断是否启动首次滚动页面
     */
    private void checkIsShowScroll() {
        if (!XPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            //检查用户是否登录了app

        }
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvLauncherTimer != null) {
                    tvLauncherTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
