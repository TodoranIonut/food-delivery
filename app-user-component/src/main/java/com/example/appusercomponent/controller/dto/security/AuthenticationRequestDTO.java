package com.example.appusercomponent.controller.dto.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequestDTO {

    private String email;
    private String password;
}
