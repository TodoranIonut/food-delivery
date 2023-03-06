package com.example.appusercomponent.exception.exceptionType;

import com.example.appusercomponent.exception.DeliveryApplicationException;

public class ForbiddenException extends DeliveryApplicationException {

    public ForbiddenException(String message) {
        super(message);
    }
}
