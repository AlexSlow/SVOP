package com.svop.controllers.API.DTO;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
