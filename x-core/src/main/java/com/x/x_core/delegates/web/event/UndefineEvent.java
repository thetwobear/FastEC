package com.x.x_core.delegates.web.event;

import com.x.x_core.util.log.XLog;

/**
 * Created by 熊猿猿 on 2017/8/31/031.
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        XLog.e("UndefineEvent", params);
        return null;
    }
}
