package com.automobile.machineStorage.service;

import com.automobile.machineStorage.entity.Auto;
import com.automobile.machineStorage.exceptions.IllegalParameterException;
import com.automobile.machineStorage.exceptions.ResourceNotFoundException;
import com.automobile.machineStorage.model.AutoModelRequest;
import com.automobile.machineStorage.model.AutoModelResponse;
import com.automobile.machineStorage.repository.AutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AutoService {

    private final AutoRepository autoRepository;

    public List<AutoModelResponse> getAllCars() {
        List<Auto> carEntities = autoRepository.findAll();
        return carEntities.stream().map(AutoModelResponse::fromEntity).collect(Collectors.toList());
    }

    public Page<AutoModelResponse> getAllAutosByPage(int page, int size) {

        if (page < 0) {
            throw new IllegalParameterException("Page index must not be negative.");
        }

        if (size <= 0) {
            throw new IllegalParameterException("Page size must be positive.");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Auto> carEntities = autoRepository.findAll(pageable);
        List<AutoModelResponse> carList = carEntities.getContent().stream().map(AutoModelResponse::fromEntity).collect(Collectors.toList());
        return new PageImpl<>(carList, carEntities.getPageable(), carEntities.getTotalElements());

    }

    public AutoModelResponse getAutoById(Long id) throws ResourceNotFoundException {
        Auto car = autoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auto", "id", id));
        return AutoModelResponse.fromEntity(car);
    }

    public AutoModelResponse createAuto(AutoModelRequest car) {
        return AutoModelResponse.fromEntity(autoRepository.save(AutoModelRequest.toEntity(car)));
    }


    public AutoModelResponse updateAuto(Long id, AutoModelRequest carDetails) throws ResourceNotFoundException {
        Auto car = autoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auto", "id", id));
        if (carDetails.getBrand() != null) {
            car.setBrand(carDetails.getBrand());
        }
        if (carDetails.getModel() != null) {
            car.setModel(carDetails.getModel());
        }
        if (carDetails.getYear() != null) {
            car.setYear(carDetails.getYear());
        }
        if (carDetails.getEngineType() != null) {
            car.setEngineType(carDetails.getEngineType());
        }
        if (carDetails.getTransmission() != null) {
            car.setTransmission(carDetails.getTransmission());
        }
        if (carDetails.getPrice() != null) {
            car.setPrice(carDetails.getPrice());
        }
        if (carDetails.getColor() != null) {
            car.setColor(carDetails.getColor());
        }

        Auto updatedAuto = autoRepository.save(car);
        return AutoModelResponse.fromEntity(updatedAuto);
    }

    public void deleteAuto(Long id) throws ResourceNotFoundException {
        if (!autoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car", "id", id);
        }

        autoRepository.deleteById(id);

    }

    public Page<AutoModelResponse> getAllAutosByFilter(String brand, String model, Integer startYear, Integer endYear, Double startPrice, Double endPrice, String color, String transmission, String engineType, int page, int size) {
        if (brand == null) brand = "";
        if (model == null) model = "";
        if (color == null) color = "";
        if (transmission == null) transmission = "";
        if (engineType == null) engineType = "";
        if (startYear == null) startYear = Integer.MIN_VALUE;
        if (endYear == null) endYear = Integer.MAX_VALUE;
        if (startPrice == null) startPrice = Double.MIN_VALUE;
        if (endPrice == null) endPrice = Double.MAX_VALUE;

        if (page < 0) {
            throw new IllegalParameterException("Page index must not be negative.");
        }

        if (size <= 0) {
            throw new IllegalParameterException("Page size must be positive.");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Auto> carEntities = autoRepository.findByBrandContainingAndModelContainingAndYearBetweenAndPriceBetweenAndColorContainingAndTransmissionContainingAndEngineTypeContaining(
                brand, model, startYear, endYear, startPrice, endPrice, color, transmission, engineType, pageable);
        List<AutoModelResponse> carList = carEntities.getContent().stream().map(AutoModelResponse::fromEntity).collect(Collectors.toList());
        return new PageImpl<>(carList, carEntities.getPageable(), carEntities.getTotalElements());
    }


}