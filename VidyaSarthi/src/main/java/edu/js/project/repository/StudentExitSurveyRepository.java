package edu.js.project.repository;

import edu.js.project.entity.StudentExitSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentExitSurveyRepository extends JpaRepository<StudentExitSurvey, Long> {


    boolean existsByStudentId(String studentId);


    Optional<StudentExitSurvey> findByStudentId(String studentId);


    long count();

    Page<StudentExitSurvey> findAll(Pageable pageable);


    @Query("SELECT AVG(s.overallRating) FROM StudentExitSurvey s")
    Double getAverageOverallRating();

    @Query("SELECT COUNT(s) FROM StudentExitSurvey s WHERE s.overallRating >= 4")
    Long countPromoters();

    @Query("SELECT COUNT(s) FROM StudentExitSurvey s WHERE s.overallRating = 3")
    Long countPassive();

    @Query("SELECT COUNT(s) FROM StudentExitSurvey s WHERE s.overallRating <= 2")
    Long countDetractors();
}
