package com.example.appusercomponent.exception.exceptionType;

import com.example.appusercomponent.exception.DeliveryApplicationException;

public class NotFoundException extends DeliveryApplicationException {

    public NotFoundException(String message) {
        super(message);
    }
}
