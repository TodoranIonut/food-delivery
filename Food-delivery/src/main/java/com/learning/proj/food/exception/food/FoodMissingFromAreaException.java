package com.learning.proj.food.exception.food;

import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class FoodMissingFromAreaException extends NotFoundException {

    public FoodMissingFromAreaException(DeliveryArea deliveryArea) {
        super(String.format("no food found in delivery area %s", deliveryArea));
    }
}
