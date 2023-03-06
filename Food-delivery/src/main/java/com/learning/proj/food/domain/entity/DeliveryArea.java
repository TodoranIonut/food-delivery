package com.learning.proj.food.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "delivery_area")
@Data
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

    @OneToOne(mappedBy = "deliveryArea")
    private Restaurant restaurant;

    public DeliveryArea(Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
