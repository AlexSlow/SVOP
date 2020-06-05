package com.svop.service.secutity;

import com.svop.tables.Users.Role;
import com.svop.tables.Users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * сервис  для пользователей Spring (не из БД) for(@link net)
 */
public interface UserService {
    void save(@NotNull User user);
    User findByUsername(@NotNull String username);
    User findById(@NotNull Integer id) throws Exception;
    String getLocale(@NotNull String username);
    void  setLocale(@NotNull String username,String locale);
    List<Map<String, String>>  getUserAccountsInfo();
    Page<User> findAll(@NotNull Pageable pageable);
    void deleteByIdIn(@NotNull List<Integer> idl);
    void setRoles(@NotNull User user, @NotNull Set<Role> roles);
    List<User> findAll();
    void save(@NotNull List<User> users);
    List<User> find(@NotNull List<Integer> idList);

}
