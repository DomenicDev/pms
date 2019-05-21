package de.hfu.pms.eventbus;

import com.google.common.eventbus.EventBus;

public class EventBusSystem {

    private static final EventBus eventBus = new EventBus();

    public static EventBus getEventBus() {
        return eventBus;
    }

}
