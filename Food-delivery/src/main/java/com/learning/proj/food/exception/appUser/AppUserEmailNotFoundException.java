package com.learning.proj.food.exception.appUser;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class AppUserEmailNotFoundException extends NotFoundException {

    public AppUserEmailNotFoundException(String chefEmail) {
        super(String.format("app user email %s is NOT found", chefEmail));
    }
}
