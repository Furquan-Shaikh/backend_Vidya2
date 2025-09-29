package edu.js.project.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class NewSubjectListDto {

    private String regulationId;
    private int semester;
    private String branch;
    private int year;

}
