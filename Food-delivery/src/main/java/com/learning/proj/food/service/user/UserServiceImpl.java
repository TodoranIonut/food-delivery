package com.learning.proj.food.service.user;

import com.learning.proj.food.domain.entity.AppUser;
import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Restaurant;
import com.learning.proj.food.domain.entity.Rider;
import com.learning.proj.food.domain.entity.UserRole;
import com.learning.proj.food.domain.repository.AppUserRepository;
import com.learning.proj.food.domain.repository.ChefRepository;
import com.learning.proj.food.domain.repository.RestaurantRepository;
import com.learning.proj.food.domain.repository.RiderRepository;
import com.learning.proj.food.exception.appUser.AppUserEmailConflictException;
import com.learning.proj.food.exception.appUser.AppUserEmailNotFoundException;
import com.learning.proj.food.exception.appUser.AppUserIdNotFoundException;
import com.learning.proj.food.exception.appUser.AppUserPhoneNumberConflictException;
import com.learning.proj.food.exception.DeliveryApplicationException;
import com.learning.proj.food.exception.chef.ChefsMissingFromRestaurantException;
import com.learning.proj.food.exception.customer.CustomerEmailNotFoundException;
import com.learning.proj.food.exception.restaurant.RestaurantIdNotFoundException;
import com.learning.proj.food.exception.rider.RidersMissingFromAreaException;
import com.learning.proj.food.utils.DistanceCalculator;
import com.learning.proj.food.validators.AppUserValidator;
import com.learning.proj.food.validators.DeliveryAreaValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ChefRepository chefRepository;
    private final RiderRepository riderRepository;
    private final AppUserRepository appUserRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser findAppUserById(String appUserId) throws AppUserIdNotFoundException {
        return appUserRepository.findById(appUserId)
                .orElseThrow(() -> {
                    log.debug("app user with id {} is NOT found", appUserId);
                    return new AppUserIdNotFoundException(appUserId);
                });
    }

    @Override
    public AppUser findAppUserByEmail(String email) throws DeliveryApplicationException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.debug("customer with mail {} is NOT found", email);
                    return new AppUserEmailNotFoundException(email);
                });
    }

    @Override
    public AppUser findAppUserByEmailAndRole(String email, String role) throws DeliveryApplicationException {
        return appUserRepository.findByEmailAndRole(email, UserRole.valueOf(role))
                .orElseThrow(() -> {
                    log.debug("customer with mail {} is NOT found", email);
                    return new AppUserEmailNotFoundException(email);
                });
    }

    @Override
    public AppUser findAppUserByPhoneNumberAndRole(String phoneNumber, String role) throws DeliveryApplicationException {
        return appUserRepository.findByPhoneNumberAndRole(phoneNumber, UserRole.valueOf(role))
                .orElseThrow(() -> {
                    log.debug("customer with phone number {} is NOT found", phoneNumber);
                    return new CustomerEmailNotFoundException(phoneNumber);
                });
    }

    @Override
    public AppUser registerNewAppUser(AppUser appUser) throws DeliveryApplicationException {
        AppUserValidator.validate(appUser);
        checkAppUserEmailIsAvailable(appUser.getEmail(),appUser.getRole());
        checkAppUserPhoneNumberIsAvailable(appUser.getPhoneNumber(),appUser.getRole());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        log.info("insert app user new chef {}", appUser.getEmail());
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser updateAppUser(String appUserId, AppUser appUser) throws DeliveryApplicationException {
        AppUserValidator.validate(appUser);
        AppUser findUser = findAppUserById(appUserId);
        if(!findUser.getEmail().equals(appUser.getEmail())){
            checkAppUserEmailIsAvailable(appUser.getEmail(),findUser.getRole());
        }
        if(!findUser.getPhoneNumber().equals(appUser.getPhoneNumber())) {
            checkAppUserPhoneNumberIsAvailable(appUser.getPhoneNumber(), findUser.getRole());
        }
        findUser.setFirstName(appUser.getFirstName());
        findUser.setLastName(appUser.getLastName());
        findUser.setEmail(appUser.getEmail());
        findUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        findUser.setPhoneNumber(appUser.getPhoneNumber());
        appUserRepository.save(findUser);
        log.info("updated app user id {}", appUserId);
        return findUser;
    }

    @Override
    public void deleteAppUserById(String appUserId) {
        try {
            AppUser findAppUser = findAppUserById(appUserId);
            log.info("delete app user with id {}", appUserId);
            appUserRepository.delete(findAppUser);
        } catch (DeliveryApplicationException ignore) {
            log.info("attempt to delete app user with id {}", appUserId);
        }
    }

    @Override
    public void checkAppUserPhoneNumberIsAvailable(String phoneNumber, UserRole role) throws DeliveryApplicationException {
        if (appUserRepository.existsAppUserByPhoneNumberAndRole(phoneNumber,role)) {
            log.debug("app user phone number {} is NOT available for role {}", phoneNumber,role);
            throw new AppUserPhoneNumberConflictException(phoneNumber);
        } else {
            log.info("app user phone number {} is available for role {}", phoneNumber,role);
        }
    }

    @Override
    public void checkAppUserEmailIsAvailable(String email, UserRole role) throws DeliveryApplicationException {
        if (appUserRepository.existsAppUserByEmailAndRole(email,role)) {
            log.debug("app user email {} is NOT available for role {}", email, role);
            throw new AppUserEmailConflictException(email);
        }
    }

    @Override
    public Set<Chef> findChefsByRestaurantId(String restaurantId) throws DeliveryApplicationException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    log.debug("restaurant id {} not found", restaurantId);
                    return new RestaurantIdNotFoundException(restaurantId);
                });
        Set<Chef> restaurantChefs = chefRepository.findAllByRestaurant(restaurant);
        if (restaurantChefs.isEmpty()) {
            log.debug("restaurant id {} has no chef", restaurant.getRestaurantName());
            throw new ChefsMissingFromRestaurantException(restaurant.getRestaurantId());
        }
        return restaurantChefs;
    }

    @Override
    public Set<Rider> findRidersByDeliveryArea(DeliveryArea deliveryArea) throws DeliveryApplicationException {
        DeliveryAreaValidator.validate(deliveryArea);
        Set<Rider> ridersInArea = new HashSet<>(riderRepository.findAll());
        if (!ridersInArea.isEmpty()) {
            ridersInArea = ridersInArea.stream().filter(
                            rider -> DistanceCalculator.distanceBetweenGeolocationCoordinatesInMeters(
                                    rider.getDeliveryArea(), deliveryArea
                            ) < deliveryArea.getRadius())
                    .collect(Collectors.toSet());
        }
        if (ridersInArea.isEmpty()) {
            log.debug("no rider found on radius {} from coordinates {} lat {} lon", deliveryArea.getRadius(), deliveryArea.getLatitude(), deliveryArea.getLongitude());
            throw new RidersMissingFromAreaException(deliveryArea);
        }
        return ridersInArea;
    }

    @Override
    public Set<Rider> findRidersByDeliveryAreaQuery(DeliveryArea deliveryArea) throws DeliveryApplicationException {
        DeliveryAreaValidator.validate(deliveryArea);
        Set<Rider> ridersInArea = riderRepository.findAllByDeliveryArea(deliveryArea);
        if (ridersInArea.isEmpty()) {
            log.debug("no rider found on radius {} from coordinates {} lat {} lon", deliveryArea.getRadius(), deliveryArea.getLatitude(), deliveryArea.getLongitude());
            throw new RidersMissingFromAreaException(deliveryArea);
        }
        return ridersInArea;
    }
}
