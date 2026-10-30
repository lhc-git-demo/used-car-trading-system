package com.usedcar.system.controller;

import com.usedcar.system.dto.MessageRequest;
import com.usedcar.system.entity.Car;
import com.usedcar.system.entity.Message;
import com.usedcar.system.entity.User;
import com.usedcar.system.repository.CarRepository;
import com.usedcar.system.repository.MessageRepository;
import com.usedcar.system.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageRepository messageRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public MessageController(MessageRepository messageRepository, CarRepository carRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/messages")
    public List<Message> listMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/messages/car/{carId}")
    public List<Message> listByCar(@PathVariable Long carId) {
        return messageRepository.findByCarId(carId);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody MessageRequest request) {
        Car car = carRepository.findById(request.carId()).orElse(null);
        User user = userRepository.findById(request.userId()).orElse(null);
        if (car == null || user == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "车辆或用户不存在"));
        }

        Message message = Message.builder()
                .car(car)
                .user(user)
                .content(request.content())
                .contactPhone(request.contactPhone())
                .status("PENDING")
                .build();

        return ResponseEntity.ok(messageRepository.save(message));
    }

    @PutMapping("/messages/{id}/reply")
    public ResponseEntity<?> reply(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Message message = messageRepository.findById(id).orElse(null);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        String reply = body.get("reply");
        message.setReply(reply);
        message.setStatus("REPLIED");
        return ResponseEntity.ok(messageRepository.save(message));
    }
}
