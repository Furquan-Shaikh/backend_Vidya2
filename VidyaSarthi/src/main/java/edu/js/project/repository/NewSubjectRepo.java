package edu.js.project.repository;

import edu.js.project.NewEntities.NewSubject;
import edu.js.project.enums.BranchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewSubjectRepo extends JpaRepository<NewSubject,Integer> {

    Optional<NewSubject> findBySubjectCode(String subCode);

    @Query("select s from NewSubject s where s.regulationId = :regulationId and s.semester = :semester and s.branchType = :branch")
    List<NewSubject>findSubjectByFilter(@Param("regulationId")String regulationId, @Param("semester") int semester, @Param("branch") BranchType branch);

    List<NewSubject> findByRegulationIdAndSemester(String regulationId, int semester);

}
