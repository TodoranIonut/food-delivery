package com.learning.proj.food.exception.rider;

import com.learning.proj.food.exception.exceptionType.BadRequestException;

public class RiderMissingParameterException extends BadRequestException {

    public RiderMissingParameterException(String message) {
        super(String.format("missing rider entity parameters %s", message));
    }

}
