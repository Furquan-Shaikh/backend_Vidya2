package edu.js.project.dto;

import edu.js.project.entity.Regulation;
import edu.js.project.entity.Semester;
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
public class BranchDto {
    private Long id;

    private String name; // e.g., "Computer Science", "Electronics"

    private RegulationDto regulationDto;
    private Set<SemesterDto> semesterDto = new HashSet<>();
}
