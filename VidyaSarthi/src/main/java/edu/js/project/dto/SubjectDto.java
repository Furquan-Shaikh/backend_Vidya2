package edu.js.project.dto;

import edu.js.project.entity.Semester;
import edu.js.project.entity.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {

    private Long id;

    private String name;

    private String subjectCode;

    private Set<SemesterDto> semestersDto = new HashSet<>();

    private Set<UnitDto> unitDto = new HashSet<>();
}
