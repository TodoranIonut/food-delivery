package com.learning.proj.food.exception.deliveryArea;

import com.learning.proj.food.exception.exceptionType.ForbiddenException;

public class DeliveryAreaCoordinatesOutOfRangeException extends ForbiddenException {

    public DeliveryAreaCoordinatesOutOfRangeException() {
        super("delivery area coordinates %s should be between -90 < latitude < 90, -180 < longitude < 180 and radius < 30000");
    }

    public DeliveryAreaCoordinatesOutOfRangeException(String message) {
        super(message);
    }
}
