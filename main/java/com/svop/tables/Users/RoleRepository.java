package com.svop.tables.Users;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleRepository extends JpaRepository<Role,Integer> {
    void deleteByIdIn(Iterable<Integer> ids);
}
