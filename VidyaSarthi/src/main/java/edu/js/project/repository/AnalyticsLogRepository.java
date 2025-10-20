// File: src/main/java/edu/js/project/repository/AnalyticsLogRepository.java
package edu.js.project.repository;

import edu.js.project.entity.AnalyticsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnalyticsLogRepository extends JpaRepository<AnalyticsLog, Long> {

    // Find a log entry by its date
    Optional<AnalyticsLog> findByDate(LocalDate date);

    // Fetch analytics data for a specific date range (e.g., the last 28 days)
    @Query("SELECT al FROM AnalyticsLog al WHERE al.date >= :startDate ORDER BY al.date ASC")
    List<AnalyticsLog> findByDateRange(@Param("startDate") LocalDate startDate);
}
