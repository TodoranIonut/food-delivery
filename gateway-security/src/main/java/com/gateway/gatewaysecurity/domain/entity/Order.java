package com.gateway.gatewaysecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String orderId;
    private String createdCustomerId;
    private String assignedChefId;
    private String assignedRiderId;
    private Set<OrderItem> orderItems;
    private Long deliveryEstimationTimestamp;
    private DeliveryArea deliveryArea;
    private Restaurant restaurant;
    private OrderStatus orderStatus = OrderStatus.PENDING;
}
