package edu.js.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDistributionDto {
    private double facultyPercentage;
    private double studentPercentage;
    private long facultyCount;
    private long studentCount;
}