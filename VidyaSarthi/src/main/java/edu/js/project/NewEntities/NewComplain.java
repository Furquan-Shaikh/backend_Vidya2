package edu.js.project.NewEntities;

import edu.js.project.enums.ComplainType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class NewComplain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String comment;
    private String studentId;
    private String materialId;
    @Enumerated(EnumType.STRING)
    private ComplainType complainType;

}
