package com.svop.service.secutity;

import com.svop.tables.Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Это пользовательский сервис для работы уже служебным классом  UserDetailsService
 * Implementation of {@link UserService} interface
 * @autor Terekhov A.S
 * @version 1.0
 */
@Configuration
public class UserServiceImpl implements UserService {
private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class.getName());
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
        logger.info("Сохранение пользователя");
        //System.out.println("user "+user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
         // Role role =new HashSet<>();
       // role.add(roleRepository.getOne(0L));

         //  user.setPermissions(permissions);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String getLocale(String username) {
        logger.info("плучение локали");
        return (userRepository.findByUsername(username).getLocale());
    }

    @Override
    public void setLocale(String username,String locale) {
    userRepository.setLocale(username,locale);
    }

    /**
     * получение информации о текущих сессиях
     * @Autor(Terehov A.C)
     * @return
     */
    @Override
    public List<Map<String, String>>  getUserAccountsInfo() {
        logger.info("получение списка всех пользователей");
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

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteByIdIn(List<Integer> idl) {
    userRepository.deleteAllByIdIn(idl);
    }

    @Override
    public User findById(Integer id) throws Exception {
       Optional<User> user =userRepository.findById(id);
       if(user.isPresent()) return user.get();
       else throw new Exception();
    }

    @Override
    public void setRoles(@NotNull User user, @NotNull Set<Role> roles) {
       user.setRoles(roles);
       userRepository.save(user);
    }
}
