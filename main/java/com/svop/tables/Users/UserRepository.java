package com.svop.tables.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(@NotNull  String username);



    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.locale = :locale  where u.username = :username")
    void setLocale(@Param("username") String username, @Param("locale") String locale);
}
