package edu.js.project.dto;

import edu.js.project.entity.Branch;
import edu.js.project.entity.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterDto {

    private Long id;

    private int semesterNumber; // e.g., 1, 2, 3

    private BranchDto branchDto;

    private Set<SubjectDto> subjectDto = new HashSet<>();
}
