package edu.js.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Base{

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column( unique = true)
    private String phone;
    @Column(name = "student_id", nullable = false)
    private String studentId;
    @Column(nullable = false)
    private String password;
    private String address;
    @Column(nullable = false)
    private String branch;
    @Column(nullable = false)
    private String year;
    private String regulation;
    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
    private String semester;
    @JsonBackReference("std-ref")
    @OneToOne(mappedBy = "student")
    Users users;

}
