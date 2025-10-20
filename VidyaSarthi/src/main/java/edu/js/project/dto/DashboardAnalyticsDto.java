package edu.js.project.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DashboardAnalyticsDto {
    private UserDistributionDto userDistribution;
    private ContentUploadsDto contentUploads;
    private long totalUsers;
    private long totalMaterials;
    private List<TimeDataDto> timeData; // <-- ADD THIS LINE
}