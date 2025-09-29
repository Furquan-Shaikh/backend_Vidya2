package edu.js.project.dto;

import edu.js.project.NewEntities.NewRegulation;
import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.enums.MaterialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewMaterialDto {


    private int id;
    private String pdfFilename;
    private String materialId;
    private String subjectCode;
    private String facultyId;
    private MaterialType materialType;
    private String regulationId;


}
