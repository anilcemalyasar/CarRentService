package com.carrental.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private double salePrice;

    @Column
    private boolean isAvailable;

    @ManyToMany(mappedBy = "cars")
    @JsonIgnore
    private List<Company> companies;

    @OneToMany(mappedBy = "car")
    private List<Order> orders;


}