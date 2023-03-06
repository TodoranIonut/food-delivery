package com.example.appusercomponent.exception.chef;


import com.example.appusercomponent.exception.exceptionType.BadRequestException;

public class ChefMissingParameterException extends BadRequestException {

    public ChefMissingParameterException(String message) {
        super(String.format("missing chef entity parameters %s", message));
    }
}
