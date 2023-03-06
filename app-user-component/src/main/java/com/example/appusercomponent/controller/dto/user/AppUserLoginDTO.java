package com.example.appusercomponent.controller.dto.user;

import com.example.appusercomponent.domain.entity.UserRole;
import lombok.Data;


@Data
public class AppUserLoginDTO {

    private String email;
    private String password;
    private UserRole role;
}