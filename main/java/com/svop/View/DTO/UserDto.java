package com.svop.controllers.API.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    Integer id;
    private String username;
    private String password;

    public UserDto(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
