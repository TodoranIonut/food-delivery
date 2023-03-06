package com.learning.proj.food.exception.exceptionType;

import com.learning.proj.food.exception.DeliveryApplicationException;

public class NotFoundException extends DeliveryApplicationException {

    public NotFoundException(String message) {
        super(message);
    }
}
