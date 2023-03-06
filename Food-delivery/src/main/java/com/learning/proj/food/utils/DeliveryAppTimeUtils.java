package com.learning.proj.food.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DeliveryAppTimeUtils {

    public static int minutesPerKilometersDistanceAtConstantSpeed(double distanceKm, short speedKmH){
        return (int) (distanceKm / speedKmH * 60);
    }

    public static int secondsPerKilometersDistanceAtConstantSpeed(double distanceKm, short speedKmH){
        return (int) (distanceKm / speedKmH * 3600);
    }

    public static long localDateTimeToTimestamp(LocalDateTime localDateTime){
        return Timestamp.valueOf(localDateTime).getTime();
    }
}
