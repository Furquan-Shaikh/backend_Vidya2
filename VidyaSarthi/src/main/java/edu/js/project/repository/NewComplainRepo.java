package edu.js.project.repository;

import edu.js.project.NewEntities.NewComplain;
import edu.js.project.entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewComplainRepo extends JpaRepository<NewComplain, Integer> {

    List<NewComplain> findByFacultyId(String facultyId);
    List<NewComplain> findByStudentId(String studentId);

}
