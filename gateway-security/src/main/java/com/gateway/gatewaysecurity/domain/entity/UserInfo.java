package com.gateway.gatewaysecurity.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class UserInfo {

    private String id;
    private AppUser appUser;
}
