package edu.js.project.repository;

import edu.js.project.entity.Student;
import edu.js.project.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Optional<Teacher> findByFacultyId(String facultyId);

    @Query("SELECT t FROM Teacher t " +
            "WHERE (:designations IS NULL OR t.designation IN :designations) " +
            "AND   (:subjects IS NULL OR t.subject IN :subjects) " )
    List<Teacher> findFacultiesByFilters(
            @Param("designations") List<String> designations,
            @Param("subjects") List<String> subjects
    );

    Optional<Teacher> findByEmail(String email);


}
