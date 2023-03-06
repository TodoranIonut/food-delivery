package com.learning.proj.food.exception.order;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class OrdersReadyForDeliveryNotFoundInAreaException extends NotFoundException {

    public OrdersReadyForDeliveryNotFoundInAreaException() {
        super("Orders ready for delivery not found in area");
    }
}

