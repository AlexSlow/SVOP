package com.svop.tables.Users;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Permission;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="role_id")
    private Integer id;

    @Column(name="role_name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permission")
    Set<RolePermissions> permissions;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy="roles")
    private Set<User> users;

    public Role(Integer id,String name) {
        this.id=id;
        this.name = name;
    }
    public Role(String name) {
        this.name = name;
    }

    public Role() {
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

    public Set<RolePermissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<RolePermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getId().equals(role.getId()) &&
                getName().equals(role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                ", users=" + users +
                '}';
    }
}
