// File: src/main/java/edu/js/project/entity/AnalyticsLog.java
package edu.js.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class AnalyticsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private long visits = 0; // Default to 0

    // Convenience method to increment visits
    public void incrementVisits() {
        this.visits++;
    }
}
