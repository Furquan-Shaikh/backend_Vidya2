package edu.js.project.NewEntities;

import edu.js.project.enums.MaterialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewMaterial {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pdfFilename;
    private String materialId;
    private String subjectCode;
    private String facultyId;
    @Enumerated(EnumType.STRING)
    @Column(name = "material_type")
    private MaterialType materialType;
    private String regulationId;
    @Lob
    @Column(name = "material_data", columnDefinition = "LONGBLOB")
    private byte[] materialData;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private NewRegulation regulations;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private NewTeacher teacher;



}
