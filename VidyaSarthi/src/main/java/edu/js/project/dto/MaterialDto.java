package edu.js.project.dto;

import edu.js.project.entity.Subject;
import edu.js.project.entity.Teacher;
import edu.js.project.entity.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialDto {

    private Long id;
    private String title;
    private String type;
    private String url;
    private String unitCode;
    private UnitDto unitDto;
    private TeacherDto teacherDto;
    private SubjectDto subjectDto;
}
