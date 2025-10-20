package edu.js.project.repository;

import edu.js.project.dto.StudentDto;
import edu.js.project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student,Long> {


    @Query("SELECT s FROM Student s " +
            "WHERE (:semesters IS NULL OR s.semester IN :semesters) " +
            "AND   (:years IS NULL OR s.year IN :years) " +
            "AND   (:branches IS NULL OR s.branch IN :branches)")
    List<Student> findStudentsByFilters(
            @Param("semesters") List<String> semesters,
            @Param("years") List<Integer> years,
            @Param("branches") List<String> branches
    );

    Optional<Student>findByStudentId(String studentId);
    List<Student> findByRegulationIn(Set<String> regulations);

}
