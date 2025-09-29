package edu.js.project.repository;

import edu.js.project.NewEntities.NewMaterial;
import edu.js.project.enums.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewMaterialRepo extends JpaRepository< NewMaterial,Integer> {

    Optional<NewMaterial> findByMaterialId(String materialId);
    List<NewMaterial> findBySubjectCode(String subCode);
    List<NewMaterial> findBySubjectCodeAndMaterialType(String subjectCode, MaterialType materialType);


}
