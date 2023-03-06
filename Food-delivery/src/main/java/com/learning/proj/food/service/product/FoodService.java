package com.learning.proj.food.service.product;

import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Food;
import com.learning.proj.food.exception.DeliveryApplicationException;

import java.util.Set;

public interface FoodService {

    Food addNewFood(Food food) throws DeliveryApplicationException;
    Food updateFood(String foodId, Food food) throws DeliveryApplicationException;
    Food findFoodById(String foodId) throws DeliveryApplicationException;
    Set<Food> findFoodByRestaurantId(String restaurantId) throws DeliveryApplicationException;
    Set<Food> findAllFoodByDeliveryArea(DeliveryArea deliveryArea) throws DeliveryApplicationException;
}
