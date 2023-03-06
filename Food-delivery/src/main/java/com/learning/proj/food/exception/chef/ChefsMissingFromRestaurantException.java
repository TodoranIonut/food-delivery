package com.learning.proj.food.exception.chef;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class ChefsMissingFromRestaurantException extends NotFoundException {

    public ChefsMissingFromRestaurantException(String restaurantId) {
        super(String.format("restaurant id %s have no chef", restaurantId));
    }
}
