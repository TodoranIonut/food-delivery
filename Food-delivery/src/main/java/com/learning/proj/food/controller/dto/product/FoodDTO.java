package com.learning.proj.food.controller.dto.product;

import lombok.Data;

import java.net.URL;

@Data
public class FoodDTO {

    private String foodName;
    private Integer preparationMinutes;
    private String description;
    private Integer weightGrams;
    private URL pictureUrl;
    private String restaurantId;
}
