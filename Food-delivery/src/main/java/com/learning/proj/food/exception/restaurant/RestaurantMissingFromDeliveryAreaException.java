package com.learning.proj.food.exception.restaurant;

import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class RestaurantMissingFromDeliveryAreaException extends NotFoundException {

    public RestaurantMissingFromDeliveryAreaException(DeliveryArea deliveryArea) {
        super(String.format("no restaurant in delivery area %s", deliveryArea));
    }
}
