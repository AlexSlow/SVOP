package com.svop;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Test {
    @ResponseBody
    @MessageMapping("/hello1")
    @SendTo("/topic/greetings")
    public String greeting(@RequestBody String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println(message);
        return "Hello, " + message;
    }
}
