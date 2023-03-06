package com.learning.proj.food.utils;

import java.time.format.DateTimeFormatter;

public interface Constants {

    short AVERAGE_SPEED_KILOMETERS_PER_HOURS = 20;
    short MINIMUM_ORDER_DURATION_MINUTES = 10;
    short FIND_RIDER_AREA_DISTANCE_METERS = 5000;

    DateTimeFormatter DATE_TIME_ZZ_MM_YYYY_HH_MM_SS_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
    DateTimeFormatter TIME_HH_MM_FORMAT = DateTimeFormatter.ofPattern("HH:mm");



}
