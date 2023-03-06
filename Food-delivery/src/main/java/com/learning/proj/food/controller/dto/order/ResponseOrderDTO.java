package com.learning.proj.food.controller.dto.order;

import com.learning.proj.food.controller.dto.restaurant.LocationDTO;
import com.learning.proj.food.domain.entity.OrderStatus;
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
