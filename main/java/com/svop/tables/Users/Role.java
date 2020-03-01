package com.svop.tables.Users;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="rls")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="roles_id")
    private Long id;

    @Column(name="rls_name")
    private String name;

    //mappedBy- используется  для главной таблицы, указвается ИМЯ переменной? у владельца
    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "roles")
    Set<User> users;

    public Role(){}
    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


}
