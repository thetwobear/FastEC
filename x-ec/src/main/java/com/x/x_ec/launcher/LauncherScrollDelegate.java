package com.x.x_ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.x.x_core.app.AccountManger;
import com.x.x_core.app.IUserChecker;
import com.x.x_core.delegates.XDeleate;
import com.x.x_core.ui.launcher.ILauncherListener;
import com.x.x_core.ui.launcher.LauncherHolderCreator;
import com.x.x_core.ui.launcher.OnLauncherFinishTag;
import com.x.x_core.util.storage.XPreference;
import com.x.x_ec.R;

import java.util.ArrayList;

/**
 * Created by 熊猿猿 on 2017/8/14/014.
 */

public class LauncherScrollDelegate extends XDeleate implements OnItemClickListener {
    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.doc_normal, R.drawable.doc_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);

    }

    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        if (position == INTEGERS.size() - 1) {
            XPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否登录了app
            AccountManger.checkAccunt(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener!=null){
                        mILauncherListener.onLauncherFinsih(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener!=null){
                        mILauncherListener.onLauncherFinsih(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }
}
