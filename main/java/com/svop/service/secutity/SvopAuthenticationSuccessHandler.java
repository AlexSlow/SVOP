package com.svop.service.secutity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SvopAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = httpServletRequest.getSession(false);
        logger.info("Пользователь "+authentication.getName()+"с правами "+authentication.getAuthorities()+" авторизовался");

        System.out.println("Cтарт сессии"+httpServletRequest.getRequestURL()+" "+httpServletRequest);

        httpServletResponse.sendRedirect("/svop/");
    }
    }

