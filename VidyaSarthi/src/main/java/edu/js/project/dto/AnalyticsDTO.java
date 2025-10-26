package edu.js.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


public class AnalyticsDTO {



    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionBreakdown {
        private String question;
        private Long excellent;
        private Long good;
        private Long average;
        private Long fair;
        private Long poor;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FacultyOverview {
        private Long facultyId;
        private String facultyName;
        private Double overallRating;
        private Long totalFeedbacks;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FacultyDetailedStats {
        private Double averageClarity;
        private Double averagePunctuality;
        private Double averageHelpfulness;
        private Double averageFairness;
        private Double averagePreparation;
        private Double averageCommand;
        private Double averagePace;
        private Double averageTeachingAids;
        private Double averageEngagement;
        private Double averageInteraction;
        private Double averageSyllabusCoverage;
        private Double averageEvaluation;
        private Double averageAssignments;
        private Double averageDoubtHandling;
        private Double averageAvailability;
        private Double averageProfessionalism;
        private Double overallAverage;
        private Long totalResponses;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FacultyComment {
        private LocalDateTime date;
        private String course;
        private String comment;
        private String semester;
        private String branch;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExitSurveyStats {
        private Long totalResponses;
        private Double overallRecommendationScore;
        private CategoryRatings categoryRatings;
        private RecommendationBreakdown recommendationBreakdown;
        private List<QuestionBreakdown> questionBreakdown;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryRatings {
        private Double academics;
        private Double infrastructure;
        private Double placements;
        private Double studentLife;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendationBreakdown {
        private Long promoters;
        private Long passive;
        private Long detractors;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExitSurveyComment {
        private LocalDateTime date;
        private String likedMost;
        private String needsImprovement;
        private String branch;
        private Integer yearOfPassing;
    }
}
