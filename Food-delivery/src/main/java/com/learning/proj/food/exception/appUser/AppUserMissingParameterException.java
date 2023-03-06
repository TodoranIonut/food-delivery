package com.learning.proj.food.exception.appUser;

import com.learning.proj.food.exception.exceptionType.BadRequestException;

public class AppUserMissingParameterException extends BadRequestException {

    public AppUserMissingParameterException(String message) {
        super(String.format("missing app user entity parameters %s", message));
    }
}
