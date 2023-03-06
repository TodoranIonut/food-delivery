package com.gateway.gatewaysecurity.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdminRequestDTO.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = CustomerRequestDTO.class, name = "CUSTOMER"),
        @JsonSubTypes.Type(value = ChefRequestDTO.class, name = "CHEF"),
        @JsonSubTypes.Type(value = RiderRequestDTO.class, name = "RIDER")
})
public abstract class UserInfoRequestDTO {
}