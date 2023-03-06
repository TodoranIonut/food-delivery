package com.gateway.gatewaysecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private String orderItemId;
    private Food food;
    private Order order;
    private short amount;
}



