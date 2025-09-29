package edu.js.project.repository;

import edu.js.project.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    Optional<Unit> findByUnitCode(String unitCOde);

    @Query("Select u from Unit u where u.subjectCode = :subjectCode AND u.unitNumber = :unitNumber")
    Optional<Unit> findUnitByQuery(@Param("subjectCode") String subjectCode, @Param("unitNumber") int unitNumber);

}
