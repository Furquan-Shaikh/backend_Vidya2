package edu.js.project.dto;

import edu.js.project.enums.ComplainType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplainDto {


        private Integer id;
        private ComplainType type ;
        private String comment;
        private long materialId;

}
