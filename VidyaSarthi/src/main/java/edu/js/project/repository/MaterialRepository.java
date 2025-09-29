package edu.js.project.repository;

import edu.js.project.entity.Material;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material,Long> {

    List<Material> findByUnitCodeAndType(String unitCode, String type);
    List<Material> findBySubject_SubjectCodeAndType(String subjectCode,String type);
    @Query("select DISTINCT m from Material m JOIN FETCH m.teacher t where t.facultyId = :facultyId")
    List<Material> findMaterial(@Param("facultyId")String facultyId);

}
