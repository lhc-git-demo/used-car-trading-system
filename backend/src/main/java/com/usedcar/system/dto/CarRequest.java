package com.usedcar.system.dto;

import java.math.BigDecimal;

public record CarRequest(
        String title,
        String brand,
        String model,
        Integer year,
        Integer mileage,
        BigDecimal price,
        String color,
        String fuelType,
        String description,
        String imageUrl,
        String status,
        Long categoryId,
        Long ownerId
) {
}
