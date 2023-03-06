package com.learning.proj.food.exception.exceptionType;

import com.learning.proj.food.exception.DeliveryApplicationException;

public class BadRequestException extends DeliveryApplicationException {

    public BadRequestException(String message) {
        super(message);
    }
}
