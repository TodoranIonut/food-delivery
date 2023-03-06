package com.example.appusercomponent.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserNewDataDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private UserInfoRequestDTO userInfoNewData;
}