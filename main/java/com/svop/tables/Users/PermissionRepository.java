package com.svop.tables.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Permission;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permissions,Integer> {
   // List<PermissionRepository> findAllByRoles(Role role);
}
