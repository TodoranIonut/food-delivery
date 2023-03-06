package com.example.appusercomponent.exception.appUser;

import com.example.appusercomponent.exception.exceptionType.ConflictException;

public class AppUserPhoneNumberConflictException extends ConflictException {

    public AppUserPhoneNumberConflictException(String chefPhoneNumber) {
        super(String.format("app user phone number %s is unavailable", chefPhoneNumber));
    }
}
