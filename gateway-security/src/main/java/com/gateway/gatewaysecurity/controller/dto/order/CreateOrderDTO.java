package com.gateway.gatewaysecurity.controller.dto.order;

import com.gateway.gatewaysecurity.controller.dto.restaurant.LocationDTO;
import lombok.Data;

import java.util.Set;

@Data
public class CreateOrderDTO {

    private Set<OrderedFoodDTO> foodItems;
    private LocationDTO deliveryArea;
    private String restaurantId;
}
