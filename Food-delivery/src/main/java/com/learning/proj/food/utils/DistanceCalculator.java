package com.learning.proj.food.utils;

import com.learning.proj.food.domain.entity.DeliveryArea;

public class DistanceCalculator {

    /**
     * Calculate distance between 2 point with geolocation coordinates
     *
     * @return distance between points in meters
     */
    public static double distanceBetweenGeolocationCoordinatesInMeters(DeliveryArea deliveryArea1, DeliveryArea deliveryArea2) {
        //distance between coordinates (lat1,lon1) to (lat2,lon2) on earth surface is calculated with formula
        //distance = ACOS( SIN(lat1)*SIN(lat2) + COS(lat1)*COS(lat2)*COS(lon2-lon1) ) * 6.371
        //where 6.371 is earth radius in kilometers or 6.371.000 meters
        //where latitude,longitude degrees should be converted in radians
        int earthRadiusMeters = 6371000;
        double latitude1Radians = Math.toRadians(deliveryArea1.getLatitude());
        double longitude1Radians = Math.toRadians(deliveryArea1.getLongitude());
        double latitude2Radians = Math.toRadians(deliveryArea2.getLatitude());
        double longitude2Radians = Math.toRadians(deliveryArea1.getLongitude());
        return Math.acos(Math.sin(latitude1Radians) * Math.sin(latitude2Radians) + Math.cos(latitude1Radians) * Math.cos(latitude2Radians) * Math.cos(longitude2Radians - longitude1Radians)) * earthRadiusMeters;
    }

    public static double distanceBetweenGeolocationCoordinatesInKilometers(DeliveryArea deliveryArea1, DeliveryArea deliveryArea2) {
        return distanceBetweenGeolocationCoordinatesInMeters(deliveryArea1, deliveryArea2) / 1000;
    }

    public static int minutesPerDistance(double distanceKm, short speedKmH) {
        return (int) (distanceKm / speedKmH * 3600);
    }
}
