package com.gateway.gatewaysecurity.security.config;

import com.gateway.gatewaysecurity.controller.dto.user.AppUserLoginDTO;
import com.gateway.gatewaysecurity.controller.feign.AppUserClient;
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AppUserClient appUserController;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public static AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                ResponseEntity<AppUserLoginDTO> responseAppUserDTO = appUserController.getAppUserAuthenticationDetails(username);

                if (responseAppUserDTO.getStatusCode().equals(HttpStatus.OK)) {

                    AppUserLoginDTO appUserLoginDTO = responseAppUserDTO.getBody();

                    if (appUserLoginDTO != null && appUserLoginDTO.getEmail() != null) {

                        return User.builder()
                                .username(appUserLoginDTO.getEmail())
                                .password(appUserLoginDTO.getPassword())
                                .roles(appUserLoginDTO.getRole().name())
                                .build();
                    }
                }
                throw new UsernameNotFoundException("User not found");
            }
        };
    }

//    @Bean
//    public AbstractDiscoveryClientOptionalArgs<?> discoveryClientOptionalArgs() {
//        return new Jersey1DiscoveryClientOptionalArgs();
//    }
}