package com.svop.service.secutity;

import com.svop.tables.Users.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider  implements JwtTokenProviderInterface{
  @Autowired private UserDetailsServiceImpl userDetailsService;
    //@Value("{jwt.token.secret}")
    private String secret="svop";
   // @Value("{jwt.token.expired}")
    private long validationInMillisecond=360000;
    @PostConstruct
    protected void init(){secret=Base64.getEncoder().encodeToString(secret.getBytes());}
    @Override
    public String createToken(String username, Collection<Role> roles)
    {
        Claims claims= Jwts.claims().setSubject(username);
        claims.put("roles",roles);
        Date now=new Date();
        Date validity=new Date(now.getTime()+validationInMillisecond);
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();

    }
    @Override
    public Authentication getAuthentication(String token)
    {
        UserDetails userDetails=userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());

    }
    @Override
    public String getUsername(String token)
    {
      return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Получить токен из запроса
     * @param httpServletRequest
     * @return
     */
    @Override
    public String resolveToken(HttpServletRequest httpServletRequest)
    {
        String bearerToken=httpServletRequest.getHeader("Authorization");
        if (bearerToken!=null && bearerToken.startsWith("Bearer_"))
        {
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;

    }
    @Override
    public Boolean validateToken(String token)
    {
        try {
            Jws<Claims> claimsJws=Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claimsJws.getBody().getExpiration().before(new Date()))
            {
                return false;
            }
            return true;
        }catch (JwtException|IllegalArgumentException ex)
        {
            throw  new JwtAuthentificationExeption("JWT token is expired or invalid");
        }

    }

}
