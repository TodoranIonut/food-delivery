package com.example.appusercomponent.service;

import com.example.appusercomponent.domain.entity.AppUser;
import com.example.appusercomponent.domain.entity.Chef;
import com.example.appusercomponent.domain.entity.DeliveryArea;
import com.example.appusercomponent.domain.entity.Rider;
import com.example.appusercomponent.domain.entity.UserRole;
import com.example.appusercomponent.exception.DeliveryApplicationException;

import java.util.Set;

public interface UserService {

    AppUser findAppUserById(String appUserId) throws DeliveryApplicationException;

    AppUser findAppUserByEmail(String email) throws DeliveryApplicationException;

    AppUser findAppUserByEmailAndRole(String appUserId,String role) throws DeliveryApplicationException;

    AppUser findAppUserByPhoneNumberAndRole(String appUserId,String role) throws DeliveryApplicationException;

    AppUser registerNewAppUser(AppUser appUser) throws DeliveryApplicationException;

    AppUser updateAppUser(String appUserId, AppUser appUser) throws DeliveryApplicationException;

    void deleteAppUserById(String appUserId);

    void checkAppUserPhoneNumberIsAvailable(String phoneNumber, UserRole role) throws DeliveryApplicationException;

    void checkAppUserEmailIsAvailable(String email, UserRole role) throws DeliveryApplicationException;

    Set<Chef> findChefsByRestaurantId(String restaurantId) throws DeliveryApplicationException;

    Set<Rider> findRidersByDeliveryArea(DeliveryArea deliveryArea) throws DeliveryApplicationException;

    Set<Rider> findRidersByDeliveryAreaQuery(DeliveryArea deliveryArea) throws DeliveryApplicationException;
}
