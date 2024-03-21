package com.bloducspauter.config;

import com.bloducspauter.event.UserRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
@Component
public class EventConfig {
    //接收事件
    @EventListener
    public void receiveEvent(UserRegisteredEvent event) {
        System.out.println(event);
    }
}
