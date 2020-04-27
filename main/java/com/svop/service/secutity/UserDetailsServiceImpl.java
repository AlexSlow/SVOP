package com.svop.service.secutity;

import com.svop.tables.Users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Имплементация служебного интерфейса
 * Назначение: Получение пользователя из БД, получение его ролей из БД и формирование пользователя spring
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      User user= userRepository.findByUsername(username);
        Set<GrantedAuthority> grantedAuthorities=new HashSet<>();//Официальные роли пользвателя. Теперь нам нужно подгрузить из БД обычные роль
        Set<Role>  roles=user.getRoles();
        grantedAuthorities.add(new SimpleGrantedAuthority("Гость"));
        //Перебор ролей и
        if (roles!=null)
        {

            for(Role role:roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()) );
                //Загрузить разрешения роли
                for (RolePermissions permissions : role.getPermissions()) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(permissions.getPermission().getName()));
                }
            }
        }


        return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
    }
}
