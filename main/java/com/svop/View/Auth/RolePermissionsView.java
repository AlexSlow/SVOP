package com.svop.View.Auth;

import java.util.List;

public class RolePermissionsView {
    private List<PermissionsView> list;
    private Integer idRole;

    public RolePermissionsView() {
    }

    public List<PermissionsView> getList() {
        return list;
    }

    public void setList(List<PermissionsView> list) {
        this.list = list;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    @Override
    public String toString() {
        return "RolePermissionsView{" +
                "list=" + list +
                ", idRole=" + idRole +
                '}';
    }


}
