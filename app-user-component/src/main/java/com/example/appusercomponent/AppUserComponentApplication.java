package com.example.appusercomponent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class AppUserComponentApplication {

    public static void main(String[] args) {
//        new SpringApplicationBuilder(AppUserComponentApplication.class)
//                .web(WebApplicationType.SERVLET).run(args);
        SpringApplication.run(AppUserComponentApplication.class, args);
    }

}
