package edu.js.project.repository;

import edu.js.project.NewEntities.NewSubject;
import edu.js.project.enums.BranchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NewSubjectRepo extends JpaRepository<NewSubject,Integer> {

    Optional<NewSubject> findBySubjectCode(String subCode);

    @Query("select s from NewSubject s where s.regulationId = :regulationId and s.semester = :semester and s.branchType = :branch")
    List<NewSubject>findSubjectByFilter(@Param("regulationId")String regulationId, @Param("semester") int semester, @Param("branch") BranchType branch);

    List<NewSubject> findByRegulationIdAndSemester(String regulationId, int semester);
    Set<NewSubject> findBySubjectCodeIn(Set<String> subjectCodes);

    // Find subjects by regulation, branch, and semester
    List<NewSubject> findByRegulationIdAndBranchTypeAndSemester(
            String regulationId,
            edu.js.project.enums.BranchType branchType,
            int semester
    );

    // Custom query to get subjects with their faculty for a specific regulation, branch, and semester
    @Query("SELECT s FROM NewSubject s " +
            "LEFT JOIN FETCH s.teachers " +
            "WHERE s.regulationId = :regulationId " +
            "AND s.branchType = :branchType " +
            "AND s.semester = :semester")
    List<NewSubject> findSubjectsWithFaculty(
            @Param("regulationId") String regulationId,
            @Param("branchType") edu.js.project.enums.BranchType branchType,
            @Param("semester") int semester
    );

}
