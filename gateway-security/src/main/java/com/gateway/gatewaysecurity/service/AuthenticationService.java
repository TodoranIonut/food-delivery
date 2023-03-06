package com.gateway.gatewaysecurity.service;


import com.gateway.gatewaysecurity.controller.dto.security.AuthenticationRequest;
import com.gateway.gatewaysecurity.controller.dto.security.AuthenticationResponse;
import com.gateway.gatewaysecurity.controller.dto.user.AppUserLoginDTO;
import com.gateway.gatewaysecurity.controller.feign.AppUserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserClient appUserController;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        final var user = appUserController.getAppUserAuthenticationDetails(request.getEmail());
        AppUserLoginDTO appUserLoginDTO = user.getBody();
        if(appUserLoginDTO == null) {
           throw new UsernameNotFoundException("User NOT found !!!");
        } else {
            UserDetails userDetails = User.builder()
                    .username(user.getBody().getEmail())
                    .password(user.getBody().getPassword())
                    .roles(user.getBody().getRole().name())
                    .build();

            var jwtToken = jwtService.generateToken(userDetails);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }

    }
}
