package edu.js.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentExitSurveyDTO {
    private Integer overallRating;
    private List<FeedbackItem> feedback;
    private String additionalComments;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedbackItem {
        private String question;
        private String option;
    }
}
