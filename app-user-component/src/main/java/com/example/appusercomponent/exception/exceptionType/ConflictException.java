package com.example.appusercomponent.exception.exceptionType;

import com.example.appusercomponent.exception.DeliveryApplicationException;

public class ConflictException extends DeliveryApplicationException {

    public ConflictException(String message) {
        super(message);
    }
}
