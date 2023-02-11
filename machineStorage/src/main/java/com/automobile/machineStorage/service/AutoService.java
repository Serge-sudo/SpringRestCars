package com.automobile.machineStorage.service;

import com.automobile.machineStorage.entity.Auto;
import com.automobile.machineStorage.exceptions.ResourceNotFoundException;
import com.automobile.machineStorage.model.AutoModelIn;
import com.automobile.machineStorage.model.AutoModelOut;
import com.automobile.machineStorage.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoService {

    private final AutoRepository autoRepository;

    @Autowired
    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }


    public List<AutoModelOut> getAllCars() {
        List<Auto> carEntities = autoRepository.findAll();
        return carEntities.stream().map(AutoModelOut::fromEntity).collect(Collectors.toList());
    }

    public Page<AutoModelOut> getAllAutosByPage(PageRequest p) {

        Page<Auto> carEntities = autoRepository.findAll(p);
        List<AutoModelOut> carList = carEntities.getContent().stream().map(AutoModelOut::fromEntity).collect(Collectors.toList());
        return new PageImpl<>(carList, carEntities.getPageable(), carEntities.getTotalElements());

    }

    public AutoModelOut getAutoById(Long id) throws ResourceNotFoundException {
        Auto car = autoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auto", "id", id));
        return AutoModelOut.fromEntity(car);
    }

    public AutoModelOut createAuto(AutoModelIn car) {
        return AutoModelOut.fromEntity(autoRepository.save(AutoModelIn.toEntity(car)));
    }


    public AutoModelOut updateAuto(Long id, AutoModelIn carDetails) throws ResourceNotFoundException {
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
        return AutoModelOut.fromEntity(updatedAuto);
    }

    public void deleteAuto(Long id) throws ResourceNotFoundException {
        Auto car = autoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auto", "id", id));

        autoRepository.delete(car);

    }

    public Page<AutoModelOut> getAllAutosByFilter(String brand, String model, Integer startYear, Integer endYear, Double startPrice, Double endPrice, String color, String transmission, String engineType, PageRequest pReq) {
        if (brand == null) brand = "";
        if (model == null) model = "";
        if (color == null) color = "";
        if (transmission == null) transmission = "";
        if (engineType == null) engineType = "";
        if (startYear == null) startYear = Integer.MIN_VALUE;
        if (endYear == null) endYear = Integer.MAX_VALUE;
        if (startPrice == null) startPrice = Double.MIN_VALUE;
        if (endPrice == null) endPrice = Double.MAX_VALUE;

        Page<Auto> carEntities = autoRepository.findByBrandContainingAndModelContainingAndYearBetweenAndPriceBetweenAndColorContainingAndTransmissionContainingAndEngineTypeContaining(
                brand, model, startYear, endYear, startPrice, endPrice, color, transmission, engineType, pReq);
        List<AutoModelOut> carList = carEntities.getContent().stream().map(AutoModelOut::fromEntity).collect(Collectors.toList());
        return new PageImpl<>(carList, carEntities.getPageable(), carEntities.getTotalElements());
    }


}