package com.svop.service.secutity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class SvopAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {
    public static final String REDIRECT_URL_SESSION_ATTRIBUTE_NAME = "REDIRECT_URL";
    @Autowired
            UserService userService;
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("Пользователь "+authentication.getName()+"с правами "+authentication.getAuthorities()+" авторизовался");

        String locale=userService.getLocale(SecurityContextHolder.getContext().getAuthentication().getName());
        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) httpServletRequest.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if(defaultSavedRequest != null){

            String url=defaultSavedRequest.getRedirectUrl().split(Pattern.quote("?"))[0];
           // System.out.println(url);
            getRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse,
                    url+"?lang="+locale);
        }else{
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }

    }
    }

