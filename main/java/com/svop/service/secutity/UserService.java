package com.svop.service.secutity;

import com.svop.tables.Users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * сервис  для пользователей Spring (не из БД) for(@link net)
 */
public interface UserService {
    void save(User user);
    User findByUsername(String username);
    String getLocale(String username);
    void  setLocale(String username,String locale);
    List<Map<String, String>>  getUserAccountsInfo();
    Page<User> findAll(Pageable pageable);
    void deleteByIdIn(List<Integer> idl);
}
