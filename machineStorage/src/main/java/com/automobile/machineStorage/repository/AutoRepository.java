package com.automobile.machineStorage.repository;

import com.automobile.machineStorage.entity.Auto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
    Page<Auto> findByBrandContainingAndModelContainingAndYearBetweenAndPriceBetweenAndColorContainingAndTransmissionContainingAndEngineTypeContaining(
            String brand, String model, Integer startYear, Integer endYear, Double startPrice, Double endPrice,
            String color, String transmission, String engineType, Pageable pageable);
}