package com.learning.proj.food.exception.exceptionType;

import com.learning.proj.food.exception.DeliveryApplicationException;

public class ForbiddenException extends DeliveryApplicationException {

    public ForbiddenException(String message) {
        super(message);
    }
}
