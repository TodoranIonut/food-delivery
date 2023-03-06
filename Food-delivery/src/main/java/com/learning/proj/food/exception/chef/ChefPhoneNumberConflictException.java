package com.learning.proj.food.exception.chef;

import com.learning.proj.food.exception.exceptionType.ConflictException;

public class ChefPhoneNumberConflictException extends ConflictException {

    public ChefPhoneNumberConflictException(String chefPhoneNumber) {
        super(String.format("chef phone number %s is unavailable", chefPhoneNumber));
    }
}
