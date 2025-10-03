package edu.js.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class NewComplainDto {


    private int id;
    private String comment;
    private String studentId;
    private String materialId;
    private String facultyId;
    private String complainType;
    private String complainStatus;

}
