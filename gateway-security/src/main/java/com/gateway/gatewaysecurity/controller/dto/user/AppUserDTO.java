package com.gateway.gatewaysecurity.controller.dto.user;

import com.gateway.gatewaysecurity.domain.entity.UserRole;
import lombok.Data;

@Data
public class AppUserDTO {

    private String appUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole role;
    private UserInfoDTO userInfo;
}