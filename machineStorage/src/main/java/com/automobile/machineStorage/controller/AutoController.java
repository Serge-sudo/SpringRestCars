package com.automobile.machineStorage.controller;


import com.automobile.machineStorage.model.AutoModelIn;
import com.automobile.machineStorage.model.AutoModelOut;
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
    public ResponseEntity<AutoModelOut> create(@Valid @RequestBody AutoModelIn car) {
        return new ResponseEntity<>(autoService.createAuto(car), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AutoModelOut>> readAll() {
        return new ResponseEntity<>(autoService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoModelOut> readById(@PathVariable(value = "id") Long carId) {
        return ResponseEntity.ok(autoService.getAutoById(carId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoModelOut> update(@PathVariable(value = "id") Long carId,
                                              @Valid @RequestBody AutoModelIn carDetails) {

        return ResponseEntity.ok(autoService.updateAuto(carId, carDetails));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        autoService.deleteAuto(id);
        return HttpStatus.OK;
    }

    @GetMapping("/list")
    public Page<AutoModelOut> getAllCarsbyPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return autoService.getAllAutosByPage(PageRequest.of(page, size));
    }

    @GetMapping("/filter")
    public Page<AutoModelOut> getCarsByFilter(@RequestParam(value = "brand", required = false) String brand,
                                             @RequestParam(value = "model", required = false) String model,
                                             @RequestParam(value = "startYear", required = false) Integer startYear,
                                             @RequestParam(value = "endYear", required = false) Integer endYear,
                                             @RequestParam(value = "startPrice", required = false) Double startPrice,
                                             @RequestParam(value = "endPrice", required = false) Double endPrice,
                                             @RequestParam(value = "color", required = false) String color,
                                             @RequestParam(value = "transmission", required = false) String transmission,
                                             @RequestParam(value = "engineType", required = false) String engineType,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {

        return autoService.getAllAutosByFilter(brand, model, startYear, endYear, startPrice, endPrice, color, transmission, engineType, PageRequest.of(page, size));
    }

}
