package com.svop.service.secutity;

import com.svop.service.secutity.UserService;
import com.svop.tables.Users.Role;
import com.svop.tables.Users.RoleRepository;
import com.svop.tables.Users.User;
import com.svop.tables.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Это пользовательский сервис для работы уже служебным классом  UserDetailsService
 * Implementation of {@link UserService} interface
 * @autor Terekhov A.S
 * @version 1.0
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;
    
    @Override
    public void save(User user) {
        System.out.println("user "+user);
           user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
           Set<Role> roles=new HashSet<>();
           roles.add(roleRepository.getOne(0L));

           user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String getLocale(String username) {
        return (userRepository.findByUsername(username).getLocale());
    }

    @Override
    public void setLocale(String username,String locale) {

    }

    /**
     * получение информации о текущих сессиях
     * @Autor(Terehov A.C)
     * @return
     */
    @Override
    public List<Map<String, String>>  getUserAccountsInfo() {
        List<String> user_session_tostring = sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(Object::toString)
                .collect(Collectors.toList());
        List<Map<String, String>> users = new ArrayList<>();
        for (String user_session_tostring_item : user_session_tostring) {
            String[] temp = user_session_tostring_item.split(";");
            Map<String, String> user_param = new HashMap<>();
            for (String item : temp) {
                //System.out.println(item);
                String[] map = item.split(":");
                if (map.length == 3) {
                    user_param.put(map[1].replace(" ",""), map[2]);
                } else if (map.length != 2) continue;
                user_param.put(map[0].replace(" ",""), map[1]);
            }
            users.add(user_param);

        }
        //System.out.println(users);
        return users;
    }

}
