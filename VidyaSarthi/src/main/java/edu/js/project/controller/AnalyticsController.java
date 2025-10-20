// File: src/main/java/edu/js/project/controller/AnalyticsController.java
package edu.js.project.controller;

import edu.js.project.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/VidyaSarthi")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PostMapping("/log-visit")
    public ResponseEntity<Void> logVisit() {
        analyticsService.logVisit();
        return ResponseEntity.ok().build();
    }
}
