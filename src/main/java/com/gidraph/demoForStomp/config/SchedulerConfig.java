package com.gidraph.demoForStomp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.gidraph.demoForStomp.domain.UserResponse;

@EnableScheduling
@Configuration
public class SchedulerConfig {
    
    @Autowired
    SimpMessagingTemplate template;

    public void sendAdhocMessages(){
        template.convertAndSend("/topic/user", new UserResponse("Scheduler"));
    }
}
