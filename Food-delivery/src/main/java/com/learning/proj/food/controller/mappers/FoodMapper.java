package com.learning.proj.food.controller.mappers;

import com.learning.proj.food.controller.dto.product.FoodDTO;
import com.learning.proj.food.domain.entity.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    @Mapping(source = "restaurant.restaurantId",target = "restaurantId")
    FoodDTO foodToFoodDTO(Food food);

    @Mapping(source = "restaurantId",target = "restaurant.restaurantId")
    Food foodDTOToFood(FoodDTO foodDTO);

    Set<FoodDTO> foodSetToFoodDTOSet(Set<Food> foodDTOSet);
}
