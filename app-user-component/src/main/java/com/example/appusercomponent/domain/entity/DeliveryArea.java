package com.example.appusercomponent.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "delivery_area")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryArea {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String areaId;
    private Double latitude;
    private Double longitude;
    private Integer radius;

    public DeliveryArea(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}