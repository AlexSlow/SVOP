package com.svop.service.secutity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.util.logging.Logger;

@Service //Видимо, это признак, что  это уже служебный сервис
public class SecurityServiceImpl implements SecurityService {

    public static final Logger logger=Logger.getLogger(SecurityServiceImpl.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public String findLoggedInUsername() {
      Object userDetails= SecurityContextHolder.getContext().getAuthentication().getDetails();
      if (userDetails instanceof UserDetails)
      {
        return ((UserDetails)userDetails).getUsername();
      }
      return null;
    }

    @Override
    public void autoLogin(String username, String password) {
        System.out.println("security" + username+" "+password);
        //Тут мы получаем из БД пользователя и его роли из БД. После этого возвращаем spring детали пользователя и его роли
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated())
        {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            //logger.info(String.format("Successfully %s auto logged in",username));
        }
    }
}
