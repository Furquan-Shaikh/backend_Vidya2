package edu.js.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RegulationInputDto {


    String regulation;
    String branch;
    int semester;
    List<SubjectDto> subjectDto= new ArrayList<>(6);

}
