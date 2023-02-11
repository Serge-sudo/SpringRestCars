package com.automobile.machineStorage.model;

import com.automobile.machineStorage.entity.Auto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoModelIn {

    private String brand;
    private String model;
    private Long year;

    private String engineType;
    private String transmission;
    private Double price;

    private String color;

    public static AutoModelIn fromEntity(Auto entity) {
        return AutoModelIn.builder()
                .brand(entity.getBrand())
                .model(entity.getModel())
                .year(entity.getYear())
                .color(entity.getColor())
                .transmission(entity.getTransmission())
                .engineType(entity.getEngineType())
                .price(entity.getPrice())
                .build();
    }

    public static Auto toEntity(AutoModelIn car) {
        return Auto.builder()
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .color(car.getColor())
                .transmission(car.getTransmission())
                .engineType(car.getEngineType())
                .price(car.getPrice())
                .build();
    }
}