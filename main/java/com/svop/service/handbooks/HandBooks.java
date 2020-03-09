package com.svop.service.handbooks;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public interface HandBooks {
    String save(Object note);
    String save(List<? extends Object> notes);
    String  delete(List<? extends Object> notes);
    String  delete(Object note);
}
