package com.learning.proj.food.exception.restaurant;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class RestaurantIdNotFoundException extends NotFoundException {

    public RestaurantIdNotFoundException(String restaurantId) {
        super(String.format("restaurant id %s not found", restaurantId));
    }
}
