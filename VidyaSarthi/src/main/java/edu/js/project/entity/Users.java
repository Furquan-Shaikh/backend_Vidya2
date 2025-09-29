package edu.js.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.js.project.NewEntities.NewTeacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Users extends Base{

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String roles;
    @OneToOne
    @JoinColumn( referencedColumnName = "id")
    @JsonManagedReference("admin-ref")
    private AdminClg adminClg;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonManagedReference("teacher-ref")
    private Teacher teacher;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonManagedReference("faculty-ref")
    private NewTeacher newTeacher;
    @OneToOne()
    @JoinColumn(referencedColumnName = "id")
    @JsonManagedReference("std-ref")
    private Student student;

}
