package com.gidraph.demoForStomp.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.gidraph.demoForStomp.domain.User;
import com.gidraph.demoForStomp.domain.UserResponse;

@Controller
public class UserController {
    

    @MessageMapping("/user")
    @SendTo("/topic/user")
    public UserResponse getUser(User user){
        return new UserResponse("Hello "+user.getName());
    }
}
