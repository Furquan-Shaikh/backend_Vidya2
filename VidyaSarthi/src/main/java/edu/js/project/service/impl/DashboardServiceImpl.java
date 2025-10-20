// File: src/main/java/edu/js/project/service/impl/DashboardServiceImpl.java
package edu.js.project.service.impl;

import edu.js.project.dto.ContentUploadsDto;
import edu.js.project.dto.DashboardAnalyticsDto;
import edu.js.project.dto.TimeDataDto;
import edu.js.project.dto.UserDistributionDto;
import edu.js.project.entity.AnalyticsLog;
import edu.js.project.enums.MaterialType;
import edu.js.project.repository.AnalyticsLogRepository;
import edu.js.project.repository.NewMaterialRepo;
import edu.js.project.repository.NewTeacherRepo;
import edu.js.project.repository.StudentRepository;
import edu.js.project.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final NewTeacherRepo teacherRepository;
    private final StudentRepository studentRepository;
    private final NewMaterialRepo materialRepository;
    private final AnalyticsLogRepository analyticsLogRepository; // <-- Inject the new repository

    @Override
    public DashboardAnalyticsDto getDashboardAnalytics() {
        // --- 1. User Distribution (No Change) ---
        long facultyCount = teacherRepository.count();
        long studentCount = studentRepository.count();
        long totalUsers = facultyCount + studentCount;
        UserDistributionDto userDistribution = UserDistributionDto.builder()
                .facultyCount(facultyCount)
                .studentCount(studentCount)
                .facultyPercentage((totalUsers > 0) ? (double) facultyCount / totalUsers * 100 : 0)
                .studentPercentage((totalUsers > 0) ? (double) studentCount / totalUsers * 100 : 0)
                .build();

        // --- 2. Content Uploads (No Change) ---
        long totalMaterials = materialRepository.count();
        List<Object[]> materialCounts = materialRepository.countMaterialsByType();
        Map<MaterialType, Long> uploadsByType = materialCounts.stream()
                .collect(Collectors.toMap(row -> (MaterialType) row[0], row -> (Long) row[1]));
        ContentUploadsDto contentUploads = ContentUploadsDto.builder()
                .totalUploads(totalMaterials)
                .uploadsByType(uploadsByType)
                .build();

        // --- 3. Fetch Real Time-Series Data ---
        List<TimeDataDto> timeData = getRealTimeData(totalUsers);

        // --- 4. Assemble the final DTO ---
        return DashboardAnalyticsDto.builder()
                .userDistribution(userDistribution)
                .contentUploads(contentUploads)
                .totalUsers(totalUsers)
                .totalMaterials(totalMaterials)
                .timeData(timeData)
                .build();
    }

    /**
     * Fetches real analytics data and aggregates it into weekly totals.
     */
    private List<TimeDataDto> getRealTimeData(long totalUsers) {
        // Fetch the last 28 days of data
        LocalDate startDate = LocalDate.now().minusDays(28);
        List<AnalyticsLog> logs = analyticsLogRepository.findByDateRange(startDate);

        List<TimeDataDto> weeklyData = new ArrayList<>();
        Random random = new Random(); // Still needed for dummy "Active Users"

        for (int i = 0; i < 4; i++) {
            LocalDate weekStart = LocalDate.now().minusDays((3 - i) * 7);
            LocalDate weekEnd = weekStart.plusDays(6);

            // Sum visits for the current week
            long weeklyVisits = logs.stream()
                    .filter(log -> !log.getDate().isBefore(weekStart) && !log.getDate().isAfter(weekEnd))
                    .mapToLong(AnalyticsLog::getVisits)
                    .sum();

            // NOTE: "Active Users" is harder to track. We will keep it as a random
            // percentage of total users for this example.
            long activeUsers = totalUsers > 0 ? (long) (totalUsers * (0.4 + random.nextDouble() * 0.2)) : 0;

            weeklyData.add(TimeDataDto.builder()
                    .name("Week " + (i + 1))
                    .visits(weeklyVisits)
                    .activeUsers(activeUsers) // Using dummy data for active users
                    .build());
        }
        return weeklyData;
    }
}
