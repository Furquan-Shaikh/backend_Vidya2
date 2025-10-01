package edu.js.project.repository;

import edu.js.project.NewEntities.NewMaterial;
import edu.js.project.entity.Material;
import edu.js.project.enums.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewMaterialRepo extends JpaRepository< NewMaterial,Integer> {

    Optional<NewMaterial> findByMaterialId(String materialId);
    List<NewMaterial> findBySubjectCode(String subCode);
    List<NewMaterial> findBySubjectCodeAndMaterialType(String subjectCode, MaterialType materialType);
    @Query("select DISTINCT n from NewMaterial n JOIN FETCH n.teacher t where t.facultyId = :facultyId")
    List<NewMaterial> findMaterial(@Param("facultyId") String facultyId);

    List<NewMaterial> findByFacultyId(String facultyId);

}
