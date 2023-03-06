package com.learning.proj.food.controller.dto.order;

import com.learning.proj.food.controller.dto.restaurant.DeliveryAreaDTO;
import com.learning.proj.food.domain.entity.OrderStatus;
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