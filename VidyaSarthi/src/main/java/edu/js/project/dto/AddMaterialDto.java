package edu.js.project.dto;

import lombok.Data;

@Data
public class AddMaterialDto {

    private MaterialDto materialDto;
    private String subCode;
    private String facultyId;
    private String unitCode;


}
