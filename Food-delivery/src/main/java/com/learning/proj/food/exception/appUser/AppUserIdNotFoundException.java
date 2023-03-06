package com.learning.proj.food.exception.appUser;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class AppUserIdNotFoundException extends NotFoundException {

    public AppUserIdNotFoundException(String chefId) {
        super(String.format("app user id %s is NOT found", chefId));
    }
}
