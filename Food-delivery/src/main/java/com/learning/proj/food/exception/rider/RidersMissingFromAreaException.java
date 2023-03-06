package com.learning.proj.food.exception.rider;

import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class RidersMissingFromAreaException extends NotFoundException {

    public RidersMissingFromAreaException(DeliveryArea deliveryArea) {
        super(String.format("no riders found in delivery area %s", deliveryArea));
    }
}
