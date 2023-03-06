package com.learning.proj.food.exception.appUser;

import com.learning.proj.food.exception.exceptionType.ConflictException;

public class AppUserPhoneNumberConflictException extends ConflictException {

    public AppUserPhoneNumberConflictException(String chefPhoneNumber) {
        super(String.format("app user phone number %s is unavailable", chefPhoneNumber));
    }
}
