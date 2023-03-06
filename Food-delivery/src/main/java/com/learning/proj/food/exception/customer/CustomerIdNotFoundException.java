package com.learning.proj.food.exception.customer;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class CustomerIdNotFoundException extends NotFoundException {

    public CustomerIdNotFoundException(String customerId) {
        super(String.format("customer id %s is NOT found", customerId));
    }
}
