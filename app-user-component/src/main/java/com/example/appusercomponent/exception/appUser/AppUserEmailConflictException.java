package com.example.appusercomponent.exception.appUser;

import com.example.appusercomponent.exception.exceptionType.ConflictException;

public class AppUserEmailConflictException extends ConflictException {

    public AppUserEmailConflictException(String chefEmail) {
        super(String.format("app user email %s is unavailable", chefEmail));
    }
}
