package com.example.appusercomponent.exception.appUser;

import com.example.appusercomponent.exception.exceptionType.NotFoundException;

public class AppUserEmailNotFoundException extends NotFoundException {

    public AppUserEmailNotFoundException(String chefEmail) {
        super(String.format("app user email %s is NOT found", chefEmail));
    }
}
