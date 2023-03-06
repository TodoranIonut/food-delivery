package com.example.appusercomponent.exception.rider;

import com.example.appusercomponent.exception.exceptionType.BadRequestException;

public class RiderMissingParameterException extends BadRequestException {

    public RiderMissingParameterException(String message) {
        super(String.format("missing rider entity parameters %s", message));
    }

}
