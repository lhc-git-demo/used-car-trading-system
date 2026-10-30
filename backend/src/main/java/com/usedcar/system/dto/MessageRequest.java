package com.usedcar.system.dto;

public record MessageRequest(Long carId, Long userId, String content, String contactPhone) {
}
