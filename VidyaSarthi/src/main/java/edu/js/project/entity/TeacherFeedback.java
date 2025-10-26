package edu.js.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_feedback", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeacherFeedback extends Base {

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "faculty_id", nullable = false)
    private Long facultyId;

    @Column(name = "course_id", nullable = false)
    private String courseId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "rating_clarity")
    private Integer ratingClarity;

    @Column(name = "rating_punctuality")
    private Integer ratingPunctuality;

    @Column(name = "rating_helpfulness")
    private Integer ratingHelpfulness;

    @Column(name = "rating_fairness")
    private Integer ratingFairness;

    @Column(name = "rating_preparation")
    private Integer ratingPreparation;

    @Column(name = "rating_command")
    private Integer ratingCommand;

    @Column(name = "rating_pace")
    private Integer ratingPace;

    @Column(name = "rating_teaching_aids")
    private Integer ratingTeachingAids;

    @Column(name = "rating_engagement")
    private Integer ratingEngagement;

    @Column(name = "rating_interaction")
    private Integer ratingInteraction;

    @Column(name = "rating_syllabus_coverage")
    private Integer ratingSyllabusCoverage;

    @Column(name = "rating_evaluation")
    private Integer ratingEvaluation;

    @Column(name = "rating_assignments")
    private Integer ratingAssignments;

    @Column(name = "rating_doubt_handling")
    private Integer ratingDoubtHandling;

    @Column(name = "rating_availability")
    private Integer ratingAvailability;

    @Column(name = "rating_professionalism")
    private Integer ratingProfessionalism;

    @Column(name = "overall_rating")
    private Integer overallRating;

    @Column(name = "comments_text", columnDefinition = "TEXT")
    private String commentsText;

    @Column(name = "regulation")
    private String regulation;

    @Column(name = "branch")
    private String branch;

    @Column(name = "semester")
    private String semester;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        System.out.println("🔵 DEBUG: TeacherFeedback entity created at: " + createdAt);
    }
}
