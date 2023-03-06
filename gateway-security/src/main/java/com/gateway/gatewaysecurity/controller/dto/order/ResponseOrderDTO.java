package com.gateway.gatewaysecurity.controller.dto.order;

import com.gateway.gatewaysecurity.controller.dto.restaurant.LocationDTO;
import com.gateway.gatewaysecurity.domain.entity.OrderStatus;
import lombok.Data;

import java.util.Set;

@Data
public class ResponseOrderDTO {

    private String orderId;
    private String createdCustomerId;
    private String assignedChefId;
    private String assignedRiderId;
    private Set<OrderedFoodDTO> foodItems;
    private Long deliveryEstimationTimestamp;
    private LocationDTO deliveryArea;
    private String restaurantId;
    private OrderStatus orderStatus;
}
