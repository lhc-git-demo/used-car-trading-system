package com.usedcar.system.dto;

public record AuthResponse(Long id, String username, String nickname, String role, String token) {
}
