package com.learning.proj.food.controller;

import com.learning.proj.food.controller.dto.product.FoodDTO;
import com.learning.proj.food.controller.dto.restaurant.LocationDTO;
import com.learning.proj.food.controller.mappers.DeliveryAreaMapper;
import com.learning.proj.food.controller.mappers.FoodMapper;
import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Food;
import com.learning.proj.food.exception.DeliveryApplicationException;
import com.learning.proj.food.service.product.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Set;

@Transactional
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final FoodService foodService;
    private final DeliveryAreaMapper deliveryAreaMapper;
    private final FoodMapper foodMapper;

    @GetMapping("/{foodId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','CHEF')")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable @NotNull String foodId) throws DeliveryApplicationException {
        Food food = foodService.findFoodById(foodId);
        FoodDTO responseFood = foodMapper.foodToFoodDTO(food);
        return ResponseEntity.ok().body(responseFood);
    }

    @GetMapping("/byRestaurant/{restaurantId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOME','CHEF')")
    public ResponseEntity<Set<FoodDTO>> getFoodByRestaurantId(@PathVariable @NotNull String restaurantId) throws DeliveryApplicationException {
        Set<Food> foodSet = foodService.findFoodByRestaurantId(restaurantId);
        Set<FoodDTO> responseFoodDTOSet = foodMapper.foodSetToFoodDTOSet(foodSet);
        return ResponseEntity.ok().body(responseFoodDTOSet);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','CHEF')")
    public ResponseEntity<FoodDTO> createNewFood(@RequestBody @NotNull FoodDTO foodDTO) throws DeliveryApplicationException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/products/food/create").toUriString());
        Food food = foodMapper.foodDTOToFood(foodDTO);
        Food createdFood = foodService.addNewFood(food);
        FoodDTO responseFoodDTO = foodMapper.foodToFoodDTO(createdFood);
        return ResponseEntity.created(uri).body(responseFoodDTO);
    }

    @PutMapping("/update/{foodId}")
    @PreAuthorize("hasAnyRole('ADMIN','CHEF')")
    public ResponseEntity<FoodDTO> updateFood(@PathVariable @NotNull String foodId, @RequestBody @NotNull FoodDTO foodDTO) throws DeliveryApplicationException {
        Food food = foodMapper.foodDTOToFood(foodDTO);
        Food updatedFood = foodService.updateFood(foodId, food);
        FoodDTO responseFoodDTO = foodMapper.foodToFoodDTO(updatedFood);
        return ResponseEntity.ok().body(responseFoodDTO);
    }

    @GetMapping("/byDeliveryArea")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<Set<FoodDTO>> findAllFoodByDeliveryArea(@RequestBody @NotNull LocationDTO locationDTO) throws DeliveryApplicationException {
        DeliveryArea deliveryArea = deliveryAreaMapper.locationDTOToDeliveryArea(locationDTO);
        Set<Food> foodSet = foodService.findAllFoodByDeliveryArea(deliveryArea);
        Set<FoodDTO> responseFoodDTOSet = foodMapper.foodSetToFoodDTOSet(foodSet);
        return ResponseEntity.ok().body(responseFoodDTOSet);
    }
}
