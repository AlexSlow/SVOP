package com.svop.View.Auth;

import com.svop.tables.Users.Access;
import com.svop.tables.Users.Permissions;
import com.svop.tables.Users.Role;
import com.svop.tables.Users.RolePermissions;

public class PermissionsView {
    private Integer id;//Номер строки
    private  String name;
    private Boolean writeAccess;
    private Boolean readAccess;
    private Integer permissionId;

    /**
     * Если у роли есть разрешение
     * @param rolePermissions
     */
    public PermissionsView(RolePermissions rolePermissions) {
            if (rolePermissions.getAccess() == Access.Write) {
                writeAccess = true;
                readAccess = true;
            } else{
                writeAccess = false;
                readAccess=true;
            }
            this.id=rolePermissions.getId();
            this.name=rolePermissions.getPermission().getName();
            this.permissionId=rolePermissions.getPermission().getId();
    }

    /**
     * Данное разрешение отсутствует у роли
     * @param permissions
     */
    public PermissionsView(Permissions permissions) {
        writeAccess = false;
        readAccess = false;
        this.id=null;
        this.name=permissions.getName();
        this.permissionId=permissions.getId();
    }


    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public PermissionsView() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getWriteAccess() {
        return writeAccess;
    }

    public void setWriteAccess(Boolean writeAccess) {
        this.writeAccess = writeAccess;
    }

    public Boolean getReadAccess() {
        return readAccess;
    }

    public void setReadAccess(Boolean readAccess) {
        this.readAccess = readAccess;
    }

    @Override
    public String toString() {
        return "PermissionsView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", writeAccess=" + writeAccess +
                ", readAccess=" + readAccess +
                ", permissionId=" + permissionId +
                '}';
    }
}
