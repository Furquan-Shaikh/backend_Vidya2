package edu.js.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Teacher extends Base{

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column( unique = true)
    private String phone;
    @Column(name = "faculty_id", nullable = false,unique = true)
    private String facultyId;
    private String designation;
    private String subject;
    @Column(nullable = false)
    private String password;
    private String address;
    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
    @JsonBackReference("teacher-ref")
    @OneToOne(mappedBy = "teacher", optional = false,cascade = CascadeType.ALL)
    Users users;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Material> materials = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    private List<News> news = new ArrayList<>();


}
