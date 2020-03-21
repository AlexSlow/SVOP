package com.svop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path_server}")
    private String uploadPathServer;
    public void addViewControllers(ViewControllerRegistry registry) {
       // registry.addViewController("/svop/").setViewName("svop");
       // registry.addViewController("/svop/airports/").setViewName("svop/airports");
        //registry.addViewController("/svop/greeting").setViewName("greeting");
        registry.addViewController("/svop/login").setViewName("/html/login.html"); //логин это шаблон
    }
/*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/load/**").addResourceLocations("file://"+uploadPathServer+"/");
    }
*/

}
