package com.example.appusercomponent.exception.appUser;

import com.example.appusercomponent.exception.exceptionType.BadRequestException;

public class AppUserMissingParameterException extends BadRequestException {

    public AppUserMissingParameterException(String message) {
        super(String.format("missing app user entity parameters %s", message));
    }
}
