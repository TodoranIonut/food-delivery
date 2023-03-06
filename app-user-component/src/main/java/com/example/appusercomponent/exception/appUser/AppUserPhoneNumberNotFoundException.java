package com.example.appusercomponent.exception.appUser;

import com.example.appusercomponent.exception.exceptionType.NotFoundException;

public class AppUserPhoneNumberNotFoundException extends NotFoundException {

    public AppUserPhoneNumberNotFoundException(String chefPhoneNumber) {
        super(String.format("app user phone number %s is unavailable", chefPhoneNumber));
    }
}
