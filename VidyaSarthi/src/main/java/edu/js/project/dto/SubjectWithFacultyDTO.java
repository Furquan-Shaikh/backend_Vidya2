package edu.js.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectWithFacultyDTO {
    private String subjectCode;
    private String subjectName;
    private Long facultyId;
    private String facultyIdString;
    private String facultyName;
    private String facultyEmail;
    private String facultyDesignation;
}
