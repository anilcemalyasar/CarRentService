package com.carrental.service.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cars")
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String type;

    @Column
    private String model;

    @Column
    private String color;

    @Column
    private int year;
    @Column
    private int maxSpeed;

    @Lob
    @Column(name = "car_image", length = 1000)
    private byte[] carImage;
    @Column
    private double rentalFee;

    @Column
    private boolean isAvailable = true;


}