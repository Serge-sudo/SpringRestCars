package com.automobile.machineStorage.controller;


import com.automobile.machineStorage.model.AutoModelRequest;
import com.automobile.machineStorage.model.AutoModelResponse;
import com.automobile.machineStorage.service.AutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AutoController {
    private final AutoService autoService;

    @PostMapping
    public ResponseEntity<AutoModelResponse> create(@Valid @RequestBody AutoModelRequest car) {
        return new ResponseEntity<>(autoService.createAuto(car), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AutoModelResponse>> readAll() {
        return new ResponseEntity<>(autoService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoModelResponse> readById(@PathVariable(value = "id") Long carId) {
        return ResponseEntity.ok(autoService.getAutoById(carId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoModelResponse> update(@PathVariable(value = "id") Long carId,
                                                    @Valid @RequestBody AutoModelRequest carDetails) {

        return ResponseEntity.ok(autoService.updateAuto(carId, carDetails));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        autoService.deleteAuto(id);
        return HttpStatus.OK;
    }

    @GetMapping("/list")
    public Page<AutoModelResponse> getAllCarsbyPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return autoService.getAllAutosByPage(page, size);
    }

    @GetMapping("/filter")
    public Page<AutoModelResponse> getCarsByFilter(@RequestParam(value = "brand", required = false, defaultValue = "") String brand,
                                                   @RequestParam(value = "model", required = false, defaultValue = "") String model,
                                                   @RequestParam(value = "startYear", required = false, defaultValue = "" + Integer.MIN_VALUE) Integer startYear,
                                                   @RequestParam(value = "endYear", required = false, defaultValue = "" + Integer.MAX_VALUE) Integer endYear,
                                                   @RequestParam(value = "startPrice", required = false, defaultValue = "" + Double.MIN_VALUE) Double startPrice,
                                                   @RequestParam(value = "endPrice", required = false, defaultValue = "" + Double.MAX_VALUE) Double endPrice,
                                                   @RequestParam(value = "color", required = false, defaultValue = "") String color,
                                                   @RequestParam(value = "transmission", required = false, defaultValue = "") String transmission,
                                                   @RequestParam(value = "engineType", required = false, defaultValue = "") String engineType,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {

        return autoService.getAllAutosByFilter(brand, model, startYear, endYear, startPrice, endPrice, color, transmission, engineType, page, size);
    }

}
