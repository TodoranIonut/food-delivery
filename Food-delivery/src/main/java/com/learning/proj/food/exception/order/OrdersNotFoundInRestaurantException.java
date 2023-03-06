package com.learning.proj.food.exception.order;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class OrdersNotFoundInRestaurantException extends NotFoundException {

    public OrdersNotFoundInRestaurantException() {
        super("Orders not found in restaurant");
    }
}

