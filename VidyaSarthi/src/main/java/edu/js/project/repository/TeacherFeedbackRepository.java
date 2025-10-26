package edu.js.project.repository;

import edu.js.project.entity.TeacherFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherFeedbackRepository extends JpaRepository<TeacherFeedback, Long> {


    boolean existsByStudentIdAndCourseId(String studentId, String courseId);


    List<TeacherFeedback> findByFacultyId(Long facultyId);


    List<TeacherFeedback> findByFacultyIdAndBranch(Long facultyId, String branch);


    Page<TeacherFeedback> findByFacultyIdAndCommentsTextIsNotNull(Long facultyId, Pageable pageable);


    @Query("SELECT DISTINCT f.facultyId FROM TeacherFeedback f")
    List<Long> findDistinctFacultyIds();


    @Query("SELECT AVG((f.ratingClarity + f.ratingPunctuality + f.ratingHelpfulness + " +
           "f.ratingFairness + f.ratingPreparation + f.ratingCommand + f.ratingPace + " +
           "f.ratingTeachingAids + f.ratingEngagement + f.ratingInteraction + " +
           "f.ratingSyllabusCoverage + f.ratingEvaluation + f.ratingAssignments + " +
           "f.ratingDoubtHandling + f.ratingAvailability + f.ratingProfessionalism) / 16.0) " +
           "FROM TeacherFeedback f WHERE f.facultyId = :facultyId")
    Double getAverageRatingForFaculty(@Param("facultyId") Long facultyId);


    @Query("SELECT COUNT(f) FROM TeacherFeedback f WHERE f.facultyId = :facultyId AND f.ratingClarity = :rating")
    Long countByFacultyIdAndRatingClarity(@Param("facultyId") Long facultyId, @Param("rating") Integer rating);
}
