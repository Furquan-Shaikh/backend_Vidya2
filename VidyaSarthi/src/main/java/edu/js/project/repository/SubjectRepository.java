package edu.js.project.repository;

import edu.js.project.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    Optional<Subject>findBySubjectCode(String subjectCode);
    List<Subject> findBySemesters_SemesterNumber(int semNumber);

}
