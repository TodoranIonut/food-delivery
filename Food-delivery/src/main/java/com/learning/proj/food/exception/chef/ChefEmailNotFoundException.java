package com.learning.proj.food.exception.chef;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class ChefEmailNotFoundException extends NotFoundException {

    public ChefEmailNotFoundException(String chefEmail) {
        super(String.format("chef email %s is NOT found", chefEmail));
    }
}
