package edu.js.project.NewEntities;

import edu.js.project.enums.ComplainStatus;
import edu.js.project.enums.ComplainType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NewComplain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String studentId;
    @Column(nullable = false)
    private String materialId;
    @Column(nullable = false)
    private String facultyId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplainType complainType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplainStatus complainStatus;

}
