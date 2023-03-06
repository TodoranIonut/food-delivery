package com.gateway.gatewaysecurity.controller.feign;


import com.gateway.gatewaysecurity.controller.dto.restaurant.DeliveryAreaDTO;
import com.gateway.gatewaysecurity.controller.dto.user.AppUserDTO;
import com.gateway.gatewaysecurity.controller.dto.user.AppUserLoginDTO;
import com.gateway.gatewaysecurity.controller.dto.user.AppUserNewDataDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient(name = "app-user-component", url = "http://localhost:9001/api/v1/users")
public interface AppUserClient {

    @GetMapping("/api/v1/users/authentication/{email}")
    ResponseEntity<AppUserLoginDTO> getAppUserAuthenticationDetails(@PathVariable @NotNull String email);

    @GetMapping(value = "/{appUserId}")
    ResponseEntity<AppUserDTO> getAppUserById(@PathVariable @NotNull String appUserId);

    @GetMapping("/byEmail/{email}")
    ResponseEntity<AppUserDTO> getAppUserByEmail(@PathVariable @NotNull String email);

    @GetMapping("/byEmail/{email}/byRole")
    ResponseEntity<AppUserDTO> getAppUserByEmailAndRole(@PathVariable @NotNull String email, @RequestParam @NotNull String role);

    @GetMapping("/byRestaurant/{restaurantId}")
    ResponseEntity<List<AppUserDTO>> getChefsByRestaurantId(@PathVariable @NotNull String restaurantId);

    @PostMapping("/register/admin")
    ResponseEntity<AppUserDTO> registerNewAdminUser(@RequestBody @NotNull AppUserNewDataDTO appUserNewDataDTO);

    @PostMapping("/register")
    ResponseEntity<AppUserDTO> registerAppUser(@RequestBody @NotNull AppUserNewDataDTO appUserNewDataDTO);

    @PutMapping("/update/{appUserId}")
    ResponseEntity<AppUserDTO> updateAppUser(@PathVariable String appUserId, @RequestBody @NotNull AppUserNewDataDTO appUserNewDataDTO);

    @DeleteMapping("/delete/{appUserId}")
    ResponseEntity<String> deleteAppUserById(@PathVariable @NotNull String appUserId);

    @GetMapping("/ridersByDeliveryArea")
    ResponseEntity<Set<AppUserDTO>> getRidersByDeliveryArea(@RequestBody @NotNull DeliveryAreaDTO deliveryAreaDTO);
}
