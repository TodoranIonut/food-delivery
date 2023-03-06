package com.learning.proj.food.exception.rider;

import com.learning.proj.food.exception.exceptionType.NotFoundException;

public class RiderEmailNotFoundException extends NotFoundException {

    public RiderEmailNotFoundException(String riderEmail) {
        super(String.format("rider email %s is NOT found", riderEmail));
    }
}
