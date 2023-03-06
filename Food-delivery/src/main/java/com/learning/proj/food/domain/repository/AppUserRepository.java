package com.learning.proj.food.domain.repository;

import com.learning.proj.food.domain.entity.AppUser;
import com.learning.proj.food.domain.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,String> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByEmailAndRole(String email, UserRole role);

    Optional<AppUser> findByPhoneNumberAndRole(String email, UserRole role);

    boolean existsAppUserByEmailAndRole(String email,UserRole role);

    boolean existsAppUserByPhoneNumberAndRole(String phoneNumber,UserRole role);
}