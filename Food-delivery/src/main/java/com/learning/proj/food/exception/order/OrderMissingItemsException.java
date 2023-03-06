package com.learning.proj.food.exception.order;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class OrderMissingItemsException extends NotFoundException {

    public OrderMissingItemsException() {
        super("order is missing order items");
    }
}
