package edu.js.project.repository;

import edu.js.project.NewEntities.NewTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewTeacherRepo extends JpaRepository<NewTeacher,Integer> {

    Optional<NewTeacher> findByFacultyId(String facultyId);
    Optional<NewTeacher> findByEmail(String email);

}
