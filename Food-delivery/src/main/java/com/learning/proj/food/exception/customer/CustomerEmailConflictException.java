package com.learning.proj.food.exception.customer;

import com.learning.proj.food.exception.exceptionType.ConflictException;

public class CustomerEmailConflictException extends ConflictException {

    public CustomerEmailConflictException(String customerEmail) {
        super(String.format("customer email %s is unavailable", customerEmail));
    }
}
