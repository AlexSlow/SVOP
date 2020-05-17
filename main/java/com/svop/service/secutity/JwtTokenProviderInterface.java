package com.svop.service.secutity;

import com.svop.tables.Users.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface JwtTokenProviderInterface {
    public String createToken(String username, Collection<Role> roles);
    public Authentication getAuthentication(String token);
    public String getUsername(String token);
    public String resolveToken(HttpServletRequest httpServletRequest);
    public Boolean validateToken(String token);

}
