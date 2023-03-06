package com.learning.proj.food.exception.customer;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class CustomerEmailNotFoundException extends NotFoundException {

    public CustomerEmailNotFoundException(String customerEmail) {
        super(String.format("customer email %s is NOT found", customerEmail));
    }
}
