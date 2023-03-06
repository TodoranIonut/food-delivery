package com.example.appusercomponent.controller.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class UserInfoDTO {

    private String role;
    private String userInfoId;
}
