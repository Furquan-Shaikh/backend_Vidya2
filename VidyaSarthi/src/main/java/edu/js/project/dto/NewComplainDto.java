package edu.js.project.dto;

import edu.js.project.enums.ComplainType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
public class NewComplainDto {


    private int id;
    private String comment;
    private String studentId;
    private String materialId;
    private ComplainType complainType;

}
