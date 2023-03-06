package com.learning.proj.food.service.product;

import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Food;
import com.learning.proj.food.domain.entity.OrderItem;
import com.learning.proj.food.domain.entity.Restaurant;
import com.learning.proj.food.domain.repository.FoodRepository;
import com.learning.proj.food.domain.repository.RestaurantRepository;
import com.learning.proj.food.exception.food.FoodIdNotFoundException;
import com.learning.proj.food.exception.food.FoodMissingFromAreaException;
import com.learning.proj.food.exception.restaurant.RestaurantMissingFromDeliveryAreaException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @InjectMocks
    private FoodServiceImpl underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @SneakyThrows
    @Test
    void addNewFood() {
        //given
        Food givenFood = new Food();
        givenFood.setFoodName("foodNameTest");
        givenFood.setPreparationMinutes(5);
        givenFood.setDescription("description test");
        givenFood.setWeightGrams(500);
        givenFood.setPictureUrl(new URL("https://www.foodtest.com"));
        givenFood.setOrderItem(Set.of(new OrderItem()));

        //when
        underTest.addNewFood(givenFood);

        //then
        ArgumentCaptor<Food> foodCaptor = ArgumentCaptor.forClass(Food.class);
        verify(foodRepository).save(foodCaptor.capture());
        Food captureFood = foodCaptor.getValue();
        assertThat(givenFood).isEqualTo(captureFood);
    }

    @SneakyThrows
    @Test
    void updateFood() {

        //given
        Food givenFood = new Food();
        givenFood.setFoodName("foodNameTest");
        givenFood.setPreparationMinutes(5);
        givenFood.setDescription("description test");
        givenFood.setWeightGrams(500);
        givenFood.setPictureUrl(new URL("https://www.foodtest.com"));
        givenFood.setOrderItem(Set.of(new OrderItem()));

        //when
        when(foodRepository.findById(any())).thenReturn(Optional.of(givenFood));
        underTest.updateFood(null, givenFood);

        //then
        ArgumentCaptor<Food> foodCaptor = ArgumentCaptor.forClass(Food.class);
        verify(foodRepository).save(foodCaptor.capture());
        Food captureFood = foodCaptor.getValue();
        assertThat(givenFood).isEqualTo(captureFood);
    }

    @SneakyThrows
    @Test
    void findFoodById() {
        //given
        String foodId = "idTest";

        //when
        when(foodRepository.findById(any())).thenReturn(Optional.of(new Food()));
        underTest.findFoodById(foodId);

        //then
        verify(foodRepository).findById(foodId);
    }

    @SneakyThrows
    @Test
    void findAllFoodByDeliveryArea() {
        //given
        DeliveryArea givenDeliveryArea = new DeliveryArea(null, 10.0, 10.0, null, null);
        Food food1 = new Food();
        Food food2 = new Food();
        DeliveryArea da1 = new DeliveryArea(null, 10.0, 10.1, 15000, null);
        List<Food> foodMenu = Arrays.asList(food1, food2);

        Restaurant restaurant = new Restaurant(null, "restaurant1NameTest", da1, foodMenu, new HashSet<>(Collections.singleton(new Chef())));

        //when
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));

        //then
        Set<Food> findFood = underTest.findAllFoodByDeliveryArea(givenDeliveryArea);
        assertTrue(findFood.contains(food1));
    }

    @Test
    void foodMissingFromArea() {
        //given
        DeliveryArea givenDeliveryArea = new DeliveryArea(null, 10.0, 10.0, 15000, new Restaurant());

        //when
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());

        //then
        assertThatThrownBy(() -> underTest.findAllFoodByDeliveryArea(givenDeliveryArea))
                .isInstanceOf(RestaurantMissingFromDeliveryAreaException.class)
                .hasMessageContaining("no restaurant in delivery area");
    }

    @Test
    void foodNotFoundById() {
        //given
        String foodId = "idTest";

        //when
        when(foodRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> underTest.findFoodById(foodId))
                .isInstanceOf(FoodIdNotFoundException.class)
                .hasMessageContaining(String.format("food with id %s was not found", foodId));
    }

    @Test
    void foodMissingFromAreaRestaurants() {
        //given
        DeliveryArea givenDeliveryArea = new DeliveryArea(null, 10.0, 10.0, null, null);
        DeliveryArea da = new DeliveryArea(null, 10.0, 10.1, 15000, null);
        Restaurant restaurant = new Restaurant(null, "restaurant1NameTest", da, new ArrayList<>(), new HashSet<>(Collections.singleton(new Chef())));

        //when
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));

        //then
        assertThatThrownBy(() -> underTest.findAllFoodByDeliveryArea(givenDeliveryArea))
                .isInstanceOf(FoodMissingFromAreaException.class)
                .hasMessageContaining(String.format("no food found in delivery area %s", givenDeliveryArea));
    }
}