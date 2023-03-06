package com.learning.proj.food.exception.customer;

import com.learning.proj.food.exception.exceptionType.BadRequestException;

public class CustomerMissingParameterException extends BadRequestException {

    public CustomerMissingParameterException(String message) {
        super(String.format("missing customer entity parameters %s", message));
    }
}
