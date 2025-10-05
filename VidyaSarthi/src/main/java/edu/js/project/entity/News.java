package edu.js.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.js.project.NewEntities.NewTeacher;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class News{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private String describeEvents;
    private String facultyId;
    @CreationTimestamp
    @Column(nullable = false, updatable = false) // Optional: ensures it's set on creation and never changed
    private LocalDateTime publishDateTime;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private NewTeacher teacher;



}
