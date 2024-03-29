package com.svop.View.Auth;

public class RoleView {
    private Integer id;
    private String name;
    private Boolean active;

    public RoleView(Integer id, String name, Boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }


    public RoleView()
   {

   }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RoleView{" +
                "name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
