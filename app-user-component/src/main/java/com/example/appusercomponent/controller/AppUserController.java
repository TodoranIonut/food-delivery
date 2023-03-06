package com.example.appusercomponent.controller;

import com.example.appusercomponent.controller.dto.user.*;
import com.example.appusercomponent.controller.mappers.AppUserMapper;
import com.example.appusercomponent.controller.mappers.AppUserNewDataMapper;
import com.example.appusercomponent.controller.mappers.DeliveryAreaMapper;
import com.example.appusercomponent.domain.entity.AppUser;
import com.example.appusercomponent.domain.entity.Chef;
import com.example.appusercomponent.domain.entity.DeliveryArea;
import com.example.appusercomponent.domain.entity.Rider;
import com.example.appusercomponent.exception.DeliveryApplicationException;
import com.example.appusercomponent.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final UserService userService;
    private final AppUserMapper appUserMapper;
    private final AppUserNewDataMapper appUserNewDataMapper;
    private final DeliveryAreaMapper deliveryAreaMapper;

    @GetMapping("authentication/{email}")
    public ResponseEntity<AppUserLoginDTO> getAppUserAuthenticationDetails(@PathVariable @NotNull String email) throws DeliveryApplicationException {
        AppUser appUser = userService.findAppUserByEmail(email);
        AppUserLoginDTO responseAppUserLoginDTO = appUserMapper.appUserToAppUserLoginDTO(appUser);
        return ResponseEntity.ok().body(responseAppUserLoginDTO);
    }

   @GetMapping("/{appUserId}")
    public ResponseEntity<AppUserDTO> getAppUserById(@PathVariable @NotNull String appUserId) throws DeliveryApplicationException {
        AppUser appUser = userService.findAppUserById(appUserId);
        AppUserDTO appUserDTO = appUserMapper.appUserToAppUserDTO(appUser);
        return ResponseEntity.ok().body(appUserDTO);
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<AppUserDTO> getAppUserByEmail(@PathVariable @NotNull String email) throws DeliveryApplicationException {
        AppUser appUser = userService.findAppUserByEmail(email);
        AppUserDTO responseChefDTO = appUserMapper.appUserToAppUserDTO(appUser);
        return ResponseEntity.ok().body(responseChefDTO);
    }

    @GetMapping("/byEmail/{email}/byRole")
    public ResponseEntity<AppUserDTO> getAppUserByEmailAndRole(@PathVariable @NotNull String email, @RequestParam @NotNull String role) throws DeliveryApplicationException {
        AppUser appUser = userService.findAppUserByEmailAndRole(email, role);
        AppUserDTO responseChefDTO = appUserMapper.appUserToAppUserDTO(appUser);
        return ResponseEntity.ok().body(responseChefDTO);
    }

    @GetMapping("/byRestaurant/{restaurantId}")
    public ResponseEntity<List<AppUserDTO>>getChefsByRestaurantId(@PathVariable @NotNull String restaurantId) throws DeliveryApplicationException {
        Set<Chef> chefs = userService.findChefsByRestaurantId(restaurantId);
        List<AppUserDTO> responseAppUserList = appUserMapper.chefSetToAppUserDTO(chefs);
        return ResponseEntity.ok().body(responseAppUserList);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AppUserDTO> registerNewAdminUser(@RequestBody @NotNull AppUserNewDataDTO appUserNewDataDTO) throws DeliveryApplicationException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users/register/admin").toUriString());
        AppUser appUser = appUserNewDataMapper.appUserNewDataDTOToAppUser(appUserNewDataDTO);
        AppUser registeredUser = userService.registerNewAppUser(appUser);
        AppUserDTO registeredUserDTO = appUserMapper.appUserToAppUserDTO(registeredUser);
        return ResponseEntity.created(uri).body(registeredUserDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserDTO> registerAppUser(@RequestBody @NotNull AppUserNewDataDTO appUserNewDataDTO) throws DeliveryApplicationException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users/register").toUriString());
        if(appUserNewDataDTO.getUserInfoNewData() instanceof AdminRequestDTO){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        AppUser appUser = appUserNewDataMapper.appUserNewDataDTOToAppUser(appUserNewDataDTO);
        AppUser registeredUser = userService.registerNewAppUser(appUser);
        AppUserDTO registeredUserDTO = appUserMapper.appUserToAppUserDTO(registeredUser);
        return ResponseEntity.created(uri).body(registeredUserDTO);
    }

    @PutMapping("/update/{appUserId}")
    public ResponseEntity<AppUserDTO> updateAppUser(@PathVariable String appUserId, @RequestBody @NotNull AppUserNewDataDTO appUserNewDataDTO) throws DeliveryApplicationException {
        AppUser appUser = appUserNewDataMapper.appUserNewDataDTOToAppUser(appUserNewDataDTO);
        AppUser updatedUser = userService.updateAppUser(appUserId,appUser);
        AppUserDTO updatedUserDTO = appUserMapper.appUserToAppUserDTO(updatedUser);
        return ResponseEntity.ok().body(updatedUserDTO);
    }

    @DeleteMapping("/delete/{appUserId}")
    public ResponseEntity<String> deleteAppUserById(@PathVariable @NotNull String appUserId) {
        userService.deleteAppUserById(appUserId);
        return ResponseEntity.ok().body("deleted chef " + appUserId);
    }

    @GetMapping("/ridersByDeliveryArea")
    public ResponseEntity<Set<AppUserDTO>> getRidersByDeliveryArea(@RequestBody @NotNull DeliveryAreaDTO deliveryAreaDTO) throws DeliveryApplicationException {
        DeliveryArea deliveryArea = deliveryAreaMapper.deliveryAreaDToToDeliverArea(deliveryAreaDTO);
        Set<Rider> riders = userService.findRidersByDeliveryAreaQuery(deliveryArea);
        Set<AppUserDTO> riderDTOSet = appUserMapper.riderSetToAppUserDTOSet(riders);
        return ResponseEntity.ok().body(riderDTOSet);
    }
}
