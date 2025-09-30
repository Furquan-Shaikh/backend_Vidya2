package edu.js.project.NewEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.js.project.entity.Teacher;
import edu.js.project.enums.BranchType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = {"teachers"})
@Entity
@Data
public class NewSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String subjectCode;
    private String name;
    private int semester;
    @Column(name = "reg_id")
    private String regulationId;
    @Enumerated(EnumType.STRING)
    @Column(name = "branch")
    private BranchType branchType;
    @ManyToMany(mappedBy = "subjects")
    @JsonIgnore
    private Set<NewTeacher> teachers = new HashSet<>();
    @ManyToOne
    @JoinColumn
    private NewRegulation regulations;

}
