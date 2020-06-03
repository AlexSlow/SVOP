package com.svop.tables.Handbooks;

public enum ReysyStatus {
    Изменен ,Отменен,Неизменный;
    private String name;
    ReysyStatus(String name)
    {
        this.name=name;
    }
    ReysyStatus(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
