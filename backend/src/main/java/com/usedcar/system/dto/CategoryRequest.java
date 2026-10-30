package com.usedcar.system.dto;

public record CategoryRequest(String name, Long parentId, Integer sortOrder) {
}
