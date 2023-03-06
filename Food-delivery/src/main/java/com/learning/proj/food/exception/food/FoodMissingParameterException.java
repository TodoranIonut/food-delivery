package com.learning.proj.food.exception.food;

import com.learning.proj.food.exception.exceptionType.BadRequestException;

public class FoodMissingParameterException extends BadRequestException {

    public FoodMissingParameterException(String message) {
        super(String.format("missing food entity parameters: %s",message));
    }
}
