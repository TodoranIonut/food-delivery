package com.example.appusercomponent.exception.exceptionType;

import com.example.appusercomponent.exception.DeliveryApplicationException;

public class BadRequestException extends DeliveryApplicationException {

    public BadRequestException(String message) {
        super(message);
    }
}
