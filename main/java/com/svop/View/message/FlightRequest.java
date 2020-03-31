package com.svop.View.message;

import com.svop.tables.Handbooks.TypeReys;

public class FlightRequest {
    private TypeReys type;
    private Integer sezon_selected;

    public FlightRequest() {
    }

    public FlightRequest(TypeReys type, Integer sezon_selected) {
        this.type = type;
        this.sezon_selected = sezon_selected;
    }

    public TypeReys getType() {
        return type;
    }

    public void setType(TypeReys type) {
        this.type = type;
    }

    public Integer getSezon_selected() {
        return sezon_selected;
    }

    public void setSezon_selected(Integer sezon_selected) {
        this.sezon_selected = sezon_selected;
    }

    @Override
    public String toString() {
        return "FlightRequest{" +
                "type=" + type +
                ", sezon_selected=" + sezon_selected +
                '}';
    }
}
