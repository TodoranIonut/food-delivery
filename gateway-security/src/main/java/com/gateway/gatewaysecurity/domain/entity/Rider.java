package com.gateway.gatewaysecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rider extends UserInfo {

    private String vehicle;
    private DeliveryArea deliveryArea;
}
