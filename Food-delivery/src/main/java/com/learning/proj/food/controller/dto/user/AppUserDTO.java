package com.learning.proj.food.controller.dto.user;

import com.learning.proj.food.domain.entity.UserRole;
import lombok.Data;

@Data
public class AppUserDTO {

    private String appUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private UserInfoDTO userInfo;
}