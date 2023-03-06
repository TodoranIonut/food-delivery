package com.learning.proj.food.exception.chef;

import com.learning.proj.food.exception.exceptionType.BadRequestException;

public class ChefMissingParameterException extends BadRequestException {

    public ChefMissingParameterException(String message) {
        super(String.format("missing chef entity parameters %s", message));
    }
}
