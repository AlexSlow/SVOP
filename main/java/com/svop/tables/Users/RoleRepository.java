package com.svop.tables.Users;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface RoleRepository extends JpaRepository<Role,Integer> {
    void deleteByIdIn(Iterable<Integer> ids);
    Set<Role> findAllByIdIn(List<Integer> integers);
}
