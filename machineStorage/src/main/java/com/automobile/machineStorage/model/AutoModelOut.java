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
public class AutoModelOut {
    private Long id;
    private String brand;
    private String model;
    private Long year;

    private String engineType;
    private String transmission;
    private Double price;

    private String color;

    public static AutoModelOut fromEntity(Auto entity) {
        return AutoModelOut.builder()
                .id(entity.getId())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .year(entity.getYear())
                .color(entity.getColor())
                .transmission(entity.getTransmission())
                .engineType(entity.getEngineType())
                .price(entity.getPrice())
                .build();
    }

    public static Auto toEntity(AutoModelOut car) {
        return Auto.builder()
                .id(car.getId())
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