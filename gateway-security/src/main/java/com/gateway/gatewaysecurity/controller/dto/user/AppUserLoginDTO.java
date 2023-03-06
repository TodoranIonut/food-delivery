package com.gateway.gatewaysecurity.controller.dto.user;

import com.gateway.gatewaysecurity.domain.entity.UserRole;
import lombok.Data;

@Data
public class AppUserLoginDTO {

    private String email;
    private String password;
    private UserRole role;
}