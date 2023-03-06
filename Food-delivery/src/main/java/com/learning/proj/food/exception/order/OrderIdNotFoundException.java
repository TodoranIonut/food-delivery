package com.learning.proj.food.exception.order;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class OrderIdNotFoundException extends NotFoundException {

    public OrderIdNotFoundException(String orderId) {
        super(String.format("order with id %s not found",orderId));
    }
}

