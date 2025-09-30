package edu.js.project.entity;

import edu.js.project.NewEntities.NewTeacher;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class News{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private String describeEvents;
    private String facultyId;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private NewTeacher teacher;



}
