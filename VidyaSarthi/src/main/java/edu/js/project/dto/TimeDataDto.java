// File: src/main/java/edu/js/project/dto/TimeDataDto.java
package edu.js.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeDataDto {
    private String name; // e.g., "Week 1"
    private long visits;
    private long activeUsers;
}
