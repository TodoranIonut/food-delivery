package com.learning.proj.food.exception.exceptionType;

import com.learning.proj.food.exception.DeliveryApplicationException;

public class ConflictException extends DeliveryApplicationException {

    public ConflictException(String message) {
        super(message);
    }
}
