package com.x.x_core.delegates.web.event;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by 熊猿猿 on 2017/8/31/031.
 */

public class EventManager {
    private static final HashMap<String, Event> EVENTS = new HashMap<>();

    private EventManager() {
        //Application 中进行添加事件
//        addEvent("test", new TestEvent());
    }

    private static class Holder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance() {
        return Holder.INSTANCE;
    }

    public EventManager addEvent(@NonNull String name, @NonNull Event event) {
        EVENTS.put(name, event);
        return this;
    }

    public Event createEvent(@NonNull String action) {
        Event event = EVENTS.get(action);
        if (event == null) {
            return new UndefineEvent();
        }
        return event;
    }
}
