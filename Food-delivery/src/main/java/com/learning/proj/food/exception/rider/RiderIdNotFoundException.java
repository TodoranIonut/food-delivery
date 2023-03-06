package com.learning.proj.food.exception.rider;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class RiderIdNotFoundException extends NotFoundException {

    public RiderIdNotFoundException(String riderId) {
        super(String.format("rider id %s is NOT found", riderId));
    }
}
