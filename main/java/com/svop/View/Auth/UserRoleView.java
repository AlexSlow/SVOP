package com.svop.View.Auth;

import java.util.List;

public class UserRoleView {
    private Integer idUser;
    private List<RoleView> list;

    public UserRoleView() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public List<RoleView> getList() {
        return list;
    }

    public void setList(List<RoleView> list) {
        this.list = list;
    }
}
