package edu.js.project.repository;

import edu.js.project.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester,Integer> {
}
