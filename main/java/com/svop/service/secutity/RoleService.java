package com.svop.service.secutity;

import com.svop.View.Auth.PermissionsView;
import com.svop.View.Auth.RolePermissionsView;
import com.svop.View.Auth.RoleView;
import com.svop.View.Auth.UserRoleView;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RoleService {
    public void savePermissionsForRole(RolePermissionsView rolePermissionsView);
    public List<PermissionsView> getPermissionsByRole(@NotNull Integer id);
    public List<RoleView> getAllRoleByUser(@NotNull Integer userId);
    public void saveRolesForUser(@NotNull UserRoleView userRoleView);
}
