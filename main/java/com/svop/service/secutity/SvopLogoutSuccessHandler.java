package com.svop.service.secutity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SvopLogoutSuccessHandler implements LogoutSuccessHandler {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        logger.info("выход "+authentication.getName()+" "+authentication.getAuthorities());
        System.out.println("выход "+authentication.getName());
       httpServletResponse.setStatus(HttpServletResponse.SC_OK);
       httpServletResponse.sendRedirect("svop/login");
    }
}
