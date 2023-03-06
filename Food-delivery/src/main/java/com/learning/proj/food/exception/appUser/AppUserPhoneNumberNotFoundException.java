package com.learning.proj.food.exception.appUser;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class AppUserPhoneNumberNotFoundException extends NotFoundException {

    public AppUserPhoneNumberNotFoundException(String chefPhoneNumber) {
        super(String.format("app user phone number %s is unavailable", chefPhoneNumber));
    }
}
