package com.svop.View.Auth;

public class RoleView {
    Integer id;
    String name;

    public RoleView() {
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

    public RoleView(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
