package com.learning.proj.food.exception.order;

import com.learning.proj.food.exception.exceptionType.BadRequestException;

public class OrderMissingParameterException extends BadRequestException {

    public OrderMissingParameterException(String message) {
        super(String.format("missing order entity parameters: %s",message));
    }
}
