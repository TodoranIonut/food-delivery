package com.learning.proj.food.exception.food;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class FoodIdNotFoundException extends NotFoundException {

    public FoodIdNotFoundException(String foodId) {
        super(String.format("food with id %s was not found", foodId));
    }

}
