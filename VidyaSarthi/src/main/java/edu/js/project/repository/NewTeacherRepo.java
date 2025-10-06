package edu.js.project.repository;

import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewTeacherRepo extends JpaRepository<NewTeacher,Integer> {

    Optional<NewTeacher> findByFacultyId(String facultyId);
    Optional<NewTeacher> findByEmail(String email);
    @Query("SELECT t FROM NewTeacher t " +
            "WHERE (:designations IS NULL OR t.designation IN :designations) " )
    List<NewTeacher> findFacultiesByFilters(
            @Param("designations") List<String> designations,
            @Param("subjects") List<String> subjects
    );

}
