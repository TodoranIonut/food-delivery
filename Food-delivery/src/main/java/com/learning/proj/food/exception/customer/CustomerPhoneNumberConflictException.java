package com.learning.proj.food.exception.customer;

import com.learning.proj.food.exception.exceptionType.ConflictException;

public class CustomerPhoneNumberConflictException extends ConflictException {

    public CustomerPhoneNumberConflictException(String customerPhoneNumber) {
        super(String.format("customer phone number %s is unavailable", customerPhoneNumber));
    }
}
