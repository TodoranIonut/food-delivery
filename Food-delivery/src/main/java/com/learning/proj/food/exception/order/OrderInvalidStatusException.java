package com.learning.proj.food.exception.order;

import com.learning.proj.food.exception.exceptionType.BadRequestException;

public class OrderInvalidStatusException extends BadRequestException {

    public OrderInvalidStatusException(String status ) {
        super(String.format("order status %s is invalid",status));
    }
}
