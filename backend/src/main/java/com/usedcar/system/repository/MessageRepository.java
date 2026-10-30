package com.usedcar.system.repository;

import com.usedcar.system.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByCarId(Long carId);
    List<Message> findByUserId(Long userId);
}
