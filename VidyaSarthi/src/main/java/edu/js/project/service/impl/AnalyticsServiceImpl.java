// src/main/java/edu/js/project/service/impl/AnalyticsServiceImpl.java

package edu.js.project.service.impl;

import edu.js.project.entity.AnalyticsLog;
import edu.js.project.repository.AnalyticsLogRepository;
import edu.js.project.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsLogRepository analyticsLogRepository;

    @Override
    @Transactional
    public void logVisit() {
        try {
            LocalDate today = LocalDate.now();
            System.out.println("✅ [1/5] ANALYTICS_DEBUG: logVisit method entered for date: " + today);

            AnalyticsLog log = analyticsLogRepository.findByDate(today)
                    .orElseGet(() -> {
                        System.out.println("✅ [2/5] ANALYTICS_DEBUG: No existing log for today. Creating a new one.");
                        AnalyticsLog newLog = new AnalyticsLog();
                        newLog.setDate(today);
                        return newLog;
                    });

            if(log.getId() != null) {
                System.out.println("✅ [2/5] ANALYTICS_DEBUG: Found existing log for today with ID: " + log.getId());
            }

            System.out.println("✅ [3/5] ANALYTICS_DEBUG: Visits count BEFORE increment: " + log.getVisits());
            log.incrementVisits();
            System.out.println("✅ [4/5] ANALYTICS_DEBUG: Visits count AFTER increment: " + log.getVisits());

            analyticsLogRepository.save(log);
            System.out.println("✅ [5/5] ANALYTICS_DEBUG: analyticsLogRepository.save() called successfully.");

        } catch (Exception e) {
            System.err.println("❌ ANALYTICS_ERROR: An exception occurred in logVisit: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
