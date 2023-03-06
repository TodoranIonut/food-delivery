package com.learning.proj.food.service.product;

import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Food;
import com.learning.proj.food.domain.entity.Restaurant;
import com.learning.proj.food.domain.repository.FoodRepository;
import com.learning.proj.food.domain.repository.RestaurantRepository;
import com.learning.proj.food.exception.DeliveryApplicationException;
import com.learning.proj.food.exception.food.FoodIdNotFoundException;
import com.learning.proj.food.exception.food.FoodMissingFromAreaException;
import com.learning.proj.food.exception.restaurant.RestaurantIdNotFoundException;
import com.learning.proj.food.exception.restaurant.RestaurantMissingFromDeliveryAreaException;
import com.learning.proj.food.utils.DistanceCalculator;
import com.learning.proj.food.validators.DeliveryAreaValidator;
import com.learning.proj.food.validators.FoodValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Food addNewFood(Food food) throws DeliveryApplicationException {
        FoodValidator.validate(food);
        log.info("add food {}", food);
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(String foodId, Food food) throws DeliveryApplicationException {
        Food findFood = findFoodById(foodId);
        FoodValidator.validate(food);
        log.info("update food id {}", foodId);
        findFood.setFoodName(food.getFoodName());
        findFood.setFoodName(food.getFoodName());
        findFood.setOrderItem(food.getOrderItem());
        findFood.setPreparationMinutes(food.getPreparationMinutes());
        findFood.setDescription(food.getDescription());
        findFood.setPictureUrl(food.getPictureUrl());
        foodRepository.save(findFood);
        return findFood;
    }

    @Override
    public Food findFoodById(String foodId) throws FoodIdNotFoundException {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> {
                    log.debug("food with id {} is NOT found", foodId);
                    return new FoodIdNotFoundException(foodId);
                });
    }

    private Set<Restaurant> findAllRestaurantsByDeliveryArea(DeliveryArea deliveryArea) throws DeliveryApplicationException {
        Set<Restaurant> restaurantsInArea = new HashSet<>(restaurantRepository.findAll());
        if (!restaurantsInArea.isEmpty()) {
            restaurantsInArea = restaurantsInArea.stream().filter(
                            restaurant -> DistanceCalculator.distanceBetweenGeolocationCoordinatesInMeters(
                                    restaurant.getDeliveryArea(), deliveryArea
                            ) < restaurant.getDeliveryArea().getRadius())
                    .collect(Collectors.toSet());
        }
        if (restaurantsInArea.isEmpty()) {
            log.debug("no restaurant in radius {} from coordinates {} lat {} lon", deliveryArea.getRadius(), deliveryArea.getLatitude(), deliveryArea.getLongitude());
            throw new RestaurantMissingFromDeliveryAreaException(deliveryArea);
        }
        return restaurantsInArea;
    }

    @Override
    public Set<Food> findFoodByRestaurantId(String restaurantId) throws DeliveryApplicationException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    log.debug("restaurant id {} not found", restaurantId);
                    return new RestaurantIdNotFoundException(restaurantId);
                });
        return new HashSet<>(restaurant.getMenu());
    }

    @Override
    public Set<Food> findAllFoodByDeliveryArea(DeliveryArea deliveryArea) throws DeliveryApplicationException {
        DeliveryAreaValidator.validate(deliveryArea);
        Set<Restaurant> restaurantsInArea = findAllRestaurantsByDeliveryArea(deliveryArea);
        Set<Food> foodInArea = restaurantsInArea.stream().flatMap(restaurant -> restaurant.getMenu().stream()).collect(Collectors.toSet());
        if (foodInArea.isEmpty()) {
            log.debug("no food in radius {} from coordinates {} lat {} lon", deliveryArea.getRadius(), deliveryArea.getLatitude(), deliveryArea.getLongitude());
            throw new FoodMissingFromAreaException(deliveryArea);
        } else
            return foodInArea;
    }
}
