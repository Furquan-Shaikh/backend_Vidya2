package edu.js.project.dto;

import edu.js.project.entity.Branch;
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
public class RegulationDto {
    private Long id;
    private String name; // e.g., "W-24", "R20"
    private Set<BranchDto> branchesDto = new HashSet<>();
}
