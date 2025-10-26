package edu.js.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherFeedbackDTO {
    private String courseId;
    private Long facultyId;
    private String regulation;
    private String branch;
    private String semester;
    private Integer overallRating;
    private List<FeedbackQuestion> feedback;
    private String additionalComments;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedbackQuestion {
        private String question;
        private String option;
    }
}
