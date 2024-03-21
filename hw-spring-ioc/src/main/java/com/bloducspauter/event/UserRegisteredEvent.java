package com.bloducspauter.event;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
    /**
     *
     * @param source 事件源，表示谁发的源
     */
    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
