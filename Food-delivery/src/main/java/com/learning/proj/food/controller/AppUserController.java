package com.learning.proj.food.controller;

import com.learning.proj.food.controller.dto.restaurant.DeliveryAreaDTO;
import com.learning.proj.food.controller.dto.user.AdminRequestDTO;
import com.learning.proj.food.controller.dto.user.AppUserDTO;
import com.learning.proj.food.controller.dto.user.AppUserNewDataDTO;
import com.learning.proj.food.controller.mappers.AppUserMapper;
import com.learning.proj.food.controller.mappers.AppUserNewDataMapper;
import com.learning.proj.food.controller.mappers.DeliveryAreaMapper;
import com.learning.proj.food.domain.entity.AppUser;
import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Rider;
import com.learning.proj.food.exception.DeliveryApplicationException;
import com.learning.proj.food.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/{appUserId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<AppUserDTO> getAppUserById(@PathVariable @NotNull String appUserId) throws DeliveryApplicationException {
        AppUser appUser = userService.findAppUserById(appUserId);
        AppUserDTO appUserDTO = appUserMapper.appUserToAppUserDTO(appUser);
        return ResponseEntity.ok().body(appUserDTO);
    }

    @GetMapping("/byEmail/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<AppUserDTO> getAppUserByEmail(@PathVariable @NotNull String email, @RequestParam @NotNull String role) throws DeliveryApplicationException {
        AppUser appUser = userService.findAppUserByEmailAndRole(email, role);
        AppUserDTO responseChefDTO = appUserMapper.appUserToAppUserDTO(appUser);
        return ResponseEntity.ok().body(responseChefDTO);
    }

    @GetMapping("/byRestaurant/{restaurantId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<AppUserDTO>>getChefsByRestaurantId(@PathVariable @NotNull String restaurantId) throws DeliveryApplicationException {
        Set<Chef> chefs = userService.findChefsByRestaurantId(restaurantId);
        List<AppUserDTO> responseAppUserList = appUserMapper.chefSetToAppUserDTO(chefs);
        return ResponseEntity.ok().body(responseAppUserList);
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','CHEF','RIDER')")
    public ResponseEntity<AppUserDTO> updateAppUser(@PathVariable String appUserId, @RequestBody @NotNull AppUserNewDataDTO appUserNewDataDTO) throws DeliveryApplicationException {
        AppUser appUser = appUserNewDataMapper.appUserNewDataDTOToAppUser(appUserNewDataDTO);
        AppUser updatedUser = userService.updateAppUser(appUserId,appUser);
        AppUserDTO updatedUserDTO = appUserMapper.appUserToAppUserDTO(updatedUser);
        return ResponseEntity.ok().body(updatedUserDTO);
    }

    @DeleteMapping("/delete/{appUserId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteAppUserById(@PathVariable @NotNull String appUserId) {
        userService.deleteAppUserById(appUserId);
        return ResponseEntity.ok().body("deleted chef " + appUserId);
    }

    @GetMapping("/ridersByDeliveryArea")
    @PreAuthorize("hasAnyRole('ADMIN','CHEF')")
    public ResponseEntity<Set<AppUserDTO>> getRidersByDeliveryArea(@RequestBody @NotNull DeliveryAreaDTO deliveryAreaDTO) throws DeliveryApplicationException {
        DeliveryArea deliveryArea = deliveryAreaMapper.deliveryAreaDToToDeliverArea(deliveryAreaDTO);
        Set<Rider> riders = userService.findRidersByDeliveryAreaQuery(deliveryArea);
        Set<AppUserDTO> riderDTOSet = appUserMapper.riderSetToAppUserDTOSet(riders);
        return ResponseEntity.ok().body(riderDTOSet);
    }
}
