package com.example.appusercomponent.exception.appUser;

import com.example.appusercomponent.exception.exceptionType.NotFoundException;

public class AppUserIdNotFoundException extends NotFoundException {

    public AppUserIdNotFoundException(String chefId) {
        super(String.format("app user id %s is NOT found", chefId));
    }
}
