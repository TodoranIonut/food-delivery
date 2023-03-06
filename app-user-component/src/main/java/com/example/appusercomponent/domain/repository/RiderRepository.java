package com.example.appusercomponent.domain.repository;

import com.example.appusercomponent.domain.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider, String> {
}
