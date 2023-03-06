package com.gateway.gatewaysecurity.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdminDTO.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = CustomerDTO.class, name = "CUSTOMER"),
        @JsonSubTypes.Type(value = ChefDTO.class, name = "CHEF"),
        @JsonSubTypes.Type(value = RiderDTO.class, name = "RIDER")
})
public abstract class UserInfoDTO {
    private String userInfoId;
}
