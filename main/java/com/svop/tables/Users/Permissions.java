package com.svop.tables.Users;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Permission;
import java.util.Set;
@Audited
@Entity
@Table(name="permissions")
public class Permissions implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="permissions_id")
    private Integer id;

    @Column(name="permissions_name")
    private String name;

    //mappedBy- используется  для главной таблицы, указвается ИМЯ переменной? у владельца
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    Set<RolePermissions> roles;

    public Permissions(){}
    public Permissions(String name) {
        this.name = name;
    }
    public Permissions(Integer id,String name) {
        this.name = name; this.id=id;
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

    public Set<RolePermissions> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolePermissions> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Permissions{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
