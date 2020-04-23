package com.svop.message.tabloSeazon;

import com.svop.View.SeazonScheduleViews.SeazonScheduleLanguageView;

import java.util.List;
import java.util.Map;

public class TabloInitResponse {
    private String message;
    private List<SeazonScheduleLanguageView> list;
    private Map<String,String> headers;
    public TabloInitResponse(){}
    public TabloInitResponse(String message, List<SeazonScheduleLanguageView> list) {
        this.message = message;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SeazonScheduleLanguageView> getList() {
        return list;
    }

    public void setList(List<SeazonScheduleLanguageView> list) {
        this.list = list;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
