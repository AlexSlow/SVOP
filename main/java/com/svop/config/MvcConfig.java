package com.svop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
       // registry.addViewController("/svop/").setViewName("svop");
       // registry.addViewController("/svop/airports/").setViewName("svop/airports");
        //registry.addViewController("/svop/greeting").setViewName("greeting");
        registry.addViewController("/svop/login").setViewName("/html/login.html"); //логин это шаблон
    }

}
