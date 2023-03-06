package com.example.appusercomponent.exception.customer;


import com.example.appusercomponent.exception.exceptionType.BadRequestException;

public class CustomerMissingParameterException extends BadRequestException {

    public CustomerMissingParameterException(String message) {
        super(String.format("missing customer entity parameters %s", message));
    }
}
