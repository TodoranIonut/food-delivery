//package com.learning.proj.food.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.learning.proj.food.controller.DTO.restaurant.LocationDTO;
//import com.learning.proj.food.controller.mappers.FoodMapper;
//import com.learning.proj.food.domain.entity.Food;
//import com.learning.proj.food.domain.entity.Restaurant;
//import com.learning.proj.food.service.product.FoodService;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Set;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ProductsComponentController.class)
//class ProductsComponentControllerTest {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @MockBean
//    private FoodService foodService;
//
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private final FoodMapper foodMapper = Mappers.getMapper(FoodMapper.class);
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @SneakyThrows
//    @Test
//    void getFoodById() {
//        //given
//        Food food = new Food();
//        String foodId = "testFoodId";
//        String foodName = "foodNameTest";
//        food.setFoodName("foodNameTest");
//
//        //when
//        when(foodService.findFoodById(foodId)).thenReturn(food);
//
//        //then
//        MvcResult result = this.mockMvc.perform(get("/api/v1/products/{foodId}", foodId))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String foodJsonString = objectMapper.writeValueAsString(foodMapper.foodToFoodDTO(food));
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(foodJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void getFoodByRestaurantId() {
//        //given
//        Food food = new Food();
//        food.setFoodName("foodNameTest");
//        String restaurantId = "testRestaurantId";
//        Restaurant restaurant = new Restaurant();
//        restaurant.setRestaurantId(restaurantId);
//        food.setRestaurant(restaurant);
//
//        //when
//        when(foodService.findFoodByRestaurantId(restaurantId)).thenReturn(Set.of(food));
//
//        //then
//        MvcResult result = this.mockMvc.perform(get("/api/v1/products/byRestaurant/{restaurantId}", restaurantId))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String foodJsonString = objectMapper.writeValueAsString(foodMapper.foodToFoodDTO(food));
//        assertThat(result.getResponse().getContentAsString()).contains(foodJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void createNewFood() {
//        //given
//        Food food = new Food();
//        String foodName = "foodNameTest";
//        food.setFoodName("foodNameTest");
//        String restaurantId = "testRestaurantId";
//        Restaurant restaurant = new Restaurant();
//        restaurant.setRestaurantId(restaurantId);
//        food.setRestaurant(restaurant);
//
//        //when
//        when(foodService.addNewFood(any())).thenReturn(food);
//
//        //then
//        String foodJsonString = objectMapper.writeValueAsString(foodMapper.foodToFoodDTO(food));
//        MvcResult result = this.mockMvc.perform(post("/api/v1/products/create")
//                        .content(foodJsonString)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(foodJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void updateFood() {
//        //given
//        Food food = new Food();
//        String foodId = "testFoodId";
//        String foodName = "foodNameTest";
//        food.setFoodId(foodId);
//        food.setFoodName("foodNameTest");
//        String restaurantId = "testRestaurantId";
//        Restaurant restaurant = new Restaurant();
//        restaurant.setRestaurantId(restaurantId);
//        food.setRestaurant(restaurant);
//
//        //when
//        when(foodService.updateFood(any(), any())).thenReturn(food);
//
//        //then
//        String foodJsonString = objectMapper.writeValueAsString(foodMapper.foodToFoodDTO(food));
//        MvcResult result = this.mockMvc.perform(post("/api/v1/products/update/{foodId}", foodId)
//                        .content(foodJsonString)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(foodJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void findAllFoodByDeliveryArea() {
//        //given
//        Food food = new Food();
//        food.setFoodName("foodNameTest");
//        String restaurantId = "testRestaurantId";
//        Restaurant restaurant = new Restaurant();
//        restaurant.setRestaurantId(restaurantId);
//        food.setRestaurant(restaurant);
//        LocationDTO locationDTO = new LocationDTO();
//        locationDTO.setLatitude(10.0);
//        locationDTO.setLongitude(10.0);
//
//        //when
//        when(foodService.findAllFoodByDeliveryArea(any())).thenReturn(Set.of(food));
//
//        //then
//        String locationJsonString = objectMapper.writeValueAsString(locationDTO);
//        MvcResult result = this.mockMvc.perform(get("/api/v1/products/byDeliveryArea")
//                        .content(locationJsonString)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String foodJsonString = objectMapper.writeValueAsString(foodMapper.foodToFoodDTO(food));
//        assertThat(result.getResponse().getContentAsString()).contains(foodJsonString);
//    }
//}