package com.svop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication




@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass=true) //АОП
@EnableCaching    //подключение Spring Cache
public class Application {
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }

    }

