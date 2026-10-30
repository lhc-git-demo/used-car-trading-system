package com.usedcar.system.controller;

import com.usedcar.system.dto.CarRequest;
import com.usedcar.system.entity.Car;
import com.usedcar.system.entity.Category;
import com.usedcar.system.entity.User;
import com.usedcar.system.repository.CarRepository;
import com.usedcar.system.repository.CategoryRepository;
import com.usedcar.system.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CarController(CarRepository carRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/cars")
    public List<Car> listCars(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        List<Car> cars = carRepository.findAll();

        return cars.stream()
                .filter(car -> keyword == null || keyword.isBlank() ||
                        (car.getTitle() != null && car.getTitle().toLowerCase().contains(keyword.toLowerCase())) ||
                        (car.getBrand() != null && car.getBrand().toLowerCase().contains(keyword.toLowerCase())) ||
                        (car.getModel() != null && car.getModel().toLowerCase().contains(keyword.toLowerCase())))
                .filter(car -> brand == null || brand.isBlank() || Objects.equals(car.getBrand(), brand))
                .filter(car -> categoryId == null || Objects.equals(car.getCategory() != null ? car.getCategory().getId() : null, categoryId))
                .filter(car -> status == null || status.isBlank() || Objects.equals(car.getStatus(), status))
                .filter(car -> minPrice == null || car.getPrice().intValue() >= minPrice)
                .filter(car -> maxPrice == null || car.getPrice().intValue() <= maxPrice)
                .toList();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cars")
    public ResponseEntity<?> createCar(@RequestBody CarRequest request) {
        Category category = null;
        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId()).orElse(null);
        }

        User owner = userRepository.findById(request.ownerId() == null ? 2L : request.ownerId()).orElse(null);
        if (owner == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "用户不存在"));
        }

        Car car = Car.builder()
                .title(request.title())
                .brand(request.brand())
                .model(request.model())
                .year(request.year())
                .mileage(request.mileage())
                .price(request.price())
                .color(request.color())
                .fuelType(request.fuelType())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .status(request.status() == null ? "ON_SALE" : request.status())
                .category(category)
                .owner(owner)
                .build();

        return ResponseEntity.ok(carRepository.save(car));
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody CarRequest request) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }

        car.setTitle(request.title());
        car.setBrand(request.brand());
        car.setModel(request.model());
        car.setYear(request.year());
        car.setMileage(request.mileage());
        car.setPrice(request.price());
        car.setColor(request.color());
        car.setFuelType(request.fuelType());
        car.setDescription(request.description());
        car.setImageUrl(request.imageUrl());
        car.setStatus(request.status() == null ? car.getStatus() : request.status());
        if (request.categoryId() != null) {
            car.setCategory(categoryRepository.findById(request.categoryId()).orElse(car.getCategory()));
        }
        if (request.ownerId() != null) {
            car.setOwner(userRepository.findById(request.ownerId()).orElse(car.getOwner()));
        }

        return ResponseEntity.ok(carRepository.save(car));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        if (!carRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
