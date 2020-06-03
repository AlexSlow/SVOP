package com.svop.service.secutity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
//@Component
public class JwtTokenFilter extends GenericFilterBean {
    //@Autowired
    private JwtTokenProvider jwtTokenProvider;
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider)
    {
        this.jwtTokenProvider=jwtTokenProvider;
    }
    public JwtTokenFilter()
    {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token=jwtTokenProvider.resolveToken((HttpServletRequest)servletRequest);
        System.out.println("Фильтр "+ token);
        if (token!=null && jwtTokenProvider.validateToken(token))
        {
            Authentication authentication=jwtTokenProvider.getAuthentication(token);
            if (authentication!=null) SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //Если нет аунтификации, то не будем работать
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
