
package com.svop.config;

import com.svop.service.secutity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   // @Autowired
    //private DataSource dataSource;
    @Autowired
    SvopLogoutSuccessHandler svopLogoutSuccessHandler;
    @Autowired
    SvopAuthenticationSuccessHandler svopAuthenticationSuccessHandler;
    //@Autowired
   // UserService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //снять защиту с конечной точки
                http
                .requiresChannel()//Для https
                .anyRequest()
                .requiresSecure();//Для https
                http
                .csrf()
                .disable()
                // ignore our stomp endpoints since they are protected using Stomp headers
               // .ignoringAntMatchers("/gs-guide-websocket/**")  //Включить в продакшен
               // .and()
                .headers()
                //.httpStrictTransportSecurity().disable()  //Что то для https
                // allow same origin to frame our site to support iframe SockJS
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/**","/svop/login","/svop/registration","/svop/public/api/**","/public/**").permitAll()
                .anyRequest().authenticated()
                .and()
                //.apply(new JwtConfigurer(new JwtTokenFilter(new JwtTokenProvider()))) //-------------------------------------JWT
                //.and()
                .formLogin()
                .loginPage("/svop/login")
                .successHandler(svopAuthenticationSuccessHandler) //Обработчик входа пользователя
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/svop/logout") //default is /logout
                .logoutSuccessUrl("/svop/login") //default is /login?logout
                .logoutSuccessHandler(svopLogoutSuccessHandler)
                .permitAll()
                .and()
                .sessionManagement().sessionFixation().migrateSession() //Защита от фиксации сессии. При создании новой сессии атрибуты старой будут копированы
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Сессия Не создается
                //.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)//Сессия создается если ее нет
                .maximumSessions(1).sessionRegistry(sessionRegistry())//Только одна сессия

               // .expiredUrl("/svop/login")

        ;
        http.exceptionHandling().accessDeniedPage("/svop/403");
        //Сессии
       //Если сессия истекла
        // .invalidSessionUrl("/invalidSession.html");
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/fonts/**","/libs/**","/img/load/**");
         }



//Для получения менеджера аунтификации
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    //Управление сессиями
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
//Для повторной аунтификации. Есть 2 варианта, первый 2 сессии будут  вместе или 1 сессия станет недоступной
    /*
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
*/
}