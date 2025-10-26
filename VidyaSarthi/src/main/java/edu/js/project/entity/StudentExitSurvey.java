package edu.js.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_exit_survey")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentExitSurvey extends Base {

    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId;

    @Column(name = "q1_curriculum_quality")
    private String q1CurriculumQuality;

    @Column(name = "q2_teaching_quality")
    private String q2TeachingQuality;

    @Column(name = "q3_syllabus_updated")
    private String q3SyllabusUpdated;

    @Column(name = "q4_faculty_mentorship")
    private String q4FacultyMentorship;

    @Column(name = "q5_exam_fairness")
    private String q5ExamFairness;

    // Section 2: Infrastructure
    @Column(name = "q6_lab_library_quality")
    private String q6LabLibraryQuality;

    @Column(name = "q7_campus_infrastructure")
    private String q7CampusInfrastructure;

    @Column(name = "q8_wifi_it_support")
    private String q8WifiItSupport;

    @Column(name = "q9_hostel_quality")
    private String q9HostelQuality;

    @Column(name = "q10_canteen_quality")
    private String q10CanteenQuality;

    // Section 3: Placements
    @Column(name = "q11_placement_effectiveness")
    private String q11PlacementEffectiveness;

    @Column(name = "q12_career_counseling")
    private String q12CareerCounseling;

    @Column(name = "q13_workshops_seminars")
    private String q13WorkshopsSeminars;

    @Column(name = "q14_internship_support")
    private String q14InternshipSupport;

    @Column(name = "q15_alumni_network")
    private String q15AlumniNetwork;

    // Section 4: Student Life
    @Column(name = "q16_sports_activities")
    private String q16SportsActivities;

    @Column(name = "q17_admin_helpfulness")
    private String q17AdminHelpfulness;

    @Column(name = "q18_grievance_system")
    private String q18GrievanceSystem;

    @Column(name = "q19_mental_health_support")
    private String q19MentalHealthSupport;

    @Column(name = "q20_personal_growth")
    private String q20PersonalGrowth;

    @Column(name = "q21_campus_safety")
    private String q21CampusSafety;

    @Column(name = "q22_would_recommend")
    private String q22WouldRecommend;

    @Column(name = "overall_rating")
    private Integer overallRating;

    @Column(name = "additional_comments", columnDefinition = "TEXT")
    private String additionalComments;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "branch")
    private String branch;

    @Column(name = "year_of_passing")
    private Integer yearOfPassing;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        System.out.println("🔵 DEBUG: StudentExitSurvey entity created at: " + createdAt);
    }
}
