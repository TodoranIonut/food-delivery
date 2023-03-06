package com.gateway.gatewaysecurity.controller.dto.order;

import com.gateway.gatewaysecurity.controller.dto.restaurant.DeliveryAreaDTO;
import com.gateway.gatewaysecurity.domain.entity.OrderStatus;
import lombok.Data;

import java.util.Set;

@Data
public class OrderDTO {

    private String createdCustomerId;
    private String assignedChefId;
    private String assignedRiderId;
    private Set<OrderedFoodDTO> orderItems;
    private Long deliveryEstimationTimestamp;
    private DeliveryAreaDTO deliveryArea;
    private String restaurantId;
    private OrderStatus orderStatus;
}