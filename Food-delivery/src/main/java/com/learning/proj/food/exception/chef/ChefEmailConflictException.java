package com.learning.proj.food.exception.chef;

import com.learning.proj.food.exception.exceptionType.ConflictException;

public class ChefEmailConflictException extends ConflictException {

    public ChefEmailConflictException(String chefEmail) {
        super(String.format("chef email %s is unavailable", chefEmail));
    }
}
