package com.svop.tables.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(@NotNull  String username);
    Page<User> findAll(Pageable pageable);
    void deleteAllByIdIn(List<Integer> idl);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.locale = :locale  where u.username = :username")
    void setLocale(@Param("username") String username, @Param("locale") String locale);
}
