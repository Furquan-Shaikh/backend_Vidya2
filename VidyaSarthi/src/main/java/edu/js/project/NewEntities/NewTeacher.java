package edu.js.project.NewEntities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.js.project.entity.Base;
import edu.js.project.entity.Material;
import edu.js.project.entity.News;
import edu.js.project.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"subjects", "materials", "users"})
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewTeacher extends Base{


    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column( unique = true)
    private String phone;
    @Column(name = "faculty_id", nullable = false,unique = true)
    private String facultyId;
    private String designation;
    @Column(nullable = false)
    private String password;
    private String address;
    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @JsonBackReference("faculty-ref")
    @OneToOne(mappedBy = "newTeacher", optional = false,cascade = CascadeType.ALL)
    Users users;

    @ManyToMany
    @JoinTable(
            name = "faculty_subject",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @Builder.Default

    private Set<NewSubject> subjects = new HashSet<>();
    @Builder.Default
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<NewMaterial> materials = new HashSet<>();
    @Builder.Default
    @OneToMany(mappedBy = "teacher")
    private List<News> news = new ArrayList<>();

}





