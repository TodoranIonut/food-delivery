package com.learning.proj.food.exception.appUser;

import com.learning.proj.food.exception.exceptionType.ConflictException;

public class AppUserEmailConflictException extends ConflictException {

    public AppUserEmailConflictException(String chefEmail) {
        super(String.format("app user email %s is unavailable", chefEmail));
    }
}
