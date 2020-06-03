package com.svop.tables.Handbooks;

public enum TypeReys {
    Регулярный,Чартерный;
    private String name;
    TypeReys(String name)
    {
        this.name=name;
    }
    TypeReys(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
