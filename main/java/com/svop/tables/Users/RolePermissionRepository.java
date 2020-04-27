package com.svop.tables.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermissions,Integer> {
    List<RolePermissions> findAllByRole(Role role);
    List<RolePermissions> findAllByRoleId(Integer role);
    RolePermissions findByRoleAndPermission(Role role,Permissions permissions);
}
