package com.svop.tables.Users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="role_permissions")
public class RolePermissions implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="role_permission_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "role")
    Role role;

    @ManyToOne
    @JoinColumn(name = "permission")
    Permissions permission;

    @Column(name="access")
    private Access  access;

    public RolePermissions() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "RolePermissions{" +
                "id=" + id +
                ", role=" + role +
                ", permission=" + permission +
                ", access=" + access +
                '}';
    }
}
