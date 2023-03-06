//package com.learning.proj.food.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.learning.proj.food.controller.mappers.AppUserMapper;
//import com.learning.proj.food.controller.mappers.AppUserNewDataMapper;
//import com.learning.proj.food.domain.entity.AppUser;
//import com.learning.proj.food.domain.entity.Customer;
//import com.learning.proj.food.domain.entity.UserRole;
//import com.learning.proj.food.service.user.UserService;
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
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(AppUserComponentController.class)
//class AppUserComponentControllerTest {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @MockBean
//    private UserService userService;
//
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private final AppUserMapper appUserMapper = Mappers.getMapper(AppUserMapper.class);
//
//    private final AppUserNewDataMapper appUserNewDataMapper = Mappers.getMapper(AppUserNewDataMapper.class);
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @SneakyThrows
//    @Test
//    void getCustomerById() {
//        //given
//        Customer customer = new Customer();
//        String appUserId = "testId";
//        AppUser appUser = new AppUser();
//        appUser.setFirstName("firstNameTest");
//        appUser.setLastName("lastNameTest");
//        appUser.setUserInfo(customer);
//
//        //when
//        when(userService.findAppUserById(appUserId)).thenReturn(appUser);
//
//        //then
//
//        MvcResult result = this.mockMvc.perform(get("/api/v1/users/{appUserId}", appUserId))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String customerJsonString = objectMapper.writeValueAsString(appUserMapper.customerToCustomerDTO(customer));
//        assertThat(result.getResponse().getContentAsString()).contains(customerJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void getCustomerByEmail() {
//        //given
//        Customer customer = new Customer();
//        String customerEmail = "testEmail@email.com";
//        AppUser appUser = new AppUser();
//        appUser.setEmail(customerEmail);
//        appUser.setUserInfo(customer);
//
//        //when
//        when(userService.findAppUserByEmail(customerEmail)).thenReturn(appUser);
//
//        //then
//        String customerJsonString = objectMapper.writeValueAsString(appUserMapper.customerToCustomerDTO(customer));
//        MvcResult result = this.mockMvc.perform(get("/api/v1/users//byEmail/{email}/{role}", customerEmail, UserRole.CUSTOMER))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertThat(result.getResponse().getContentAsString()).contains(customerJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void registerNewCustomer() {
//        //given
//        Customer customer = new Customer();
//        AppUser appUser = new AppUser();
//        appUser.setFirstName("firstNameTest");
//        appUser.setLastName("lastNameTest");
//        appUser.setUserInfo(customer);
//
//        //when
//        when(userService.registerNewAppUser(any())).thenReturn(appUser);
//
//        //then
//        String customerJsonString = objectMapper.writeValueAsString(appUserMapper.appUserToAppUserDTO(appUser));
//        MvcResult result = this.mockMvc.perform(post("/api/v1/users/register")
//                        .content(customerJsonString)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        assertThat(result.getResponse().getContentAsString()).contains(customerJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void updateCustomer() {
//        //given
//        Customer customer = new Customer();
//        String appUserId = "testId";
//        AppUser appUser = new AppUser();
//        appUser.setFirstName("firstNameTest");
//        appUser.setLastName("lastNameTest");
//        appUser.setId(appUserId);
//        appUser.setUserInfo(customer);
//
//
//        //when
//        when(userService.updateAppUser(any(),any())).thenReturn(appUser);
//
//        //then
//        String customerJsonString = objectMapper.writeValueAsString(appUserMapper.customerToCustomerDTO(customer));
//        MvcResult result = this.mockMvc.perform(post("/api/v1/users/update/{appUserId}",appUserId)
//                        .content(customerJsonString)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertThat(result.getResponse().getContentAsString()).contains(customerJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void deleteCustomerById() {
//        //given
//        String appUserId = "customerTestId";
//
//        //then
//        this.mockMvc.perform(delete("/api/v1/users/delete/{appUserId}",appUserId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString(String.format("deleted app user %s",appUserId))));
//    }
//}