package com.learning.proj.food.exception.chef;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class ChefIdNotFoundException extends NotFoundException {

    public ChefIdNotFoundException(String chefId) {
        super(String.format("chef id %s is NOT found", chefId));
    }
}
