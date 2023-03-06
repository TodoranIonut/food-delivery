package com.learning.proj.food.validators;

import com.learning.proj.food.domain.entity.Food;
import com.learning.proj.food.exception.food.FoodMissingParameterException;

import java.util.ArrayList;
import java.util.List;

public class FoodValidator {

    /**
     * Food object fields should not be null
     */
    public static void validate(Food food) throws FoodMissingParameterException {

        List<String> messageList = new ArrayList<>();
        if(food.getFoodName() == null){
            messageList.add("foodName");
        }
        if(food.getPreparationMinutes() == null){
            messageList.add("preparationMinutes");
        }
        if(food.getDescription() == null){
            messageList.add("description");
        }
        if(food.getPictureUrl() == null){
            messageList.add("pictureUrl");
        }
        if(messageList.size() > 0){
            String exceptionMessage = String.join(", ",messageList);
            throw new FoodMissingParameterException(exceptionMessage);
        }
    }
}
