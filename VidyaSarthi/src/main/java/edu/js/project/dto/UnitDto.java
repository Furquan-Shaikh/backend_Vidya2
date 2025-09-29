package edu.js.project.dto;

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
public class UnitDto {

    private Long id;

    private String unitCode;
    private int unitNumber;
    private String title;
    private String subjectCode;
    private SubjectDto subjectDto;
    private Set<MaterialDto> materialDto = new HashSet<>();
}
