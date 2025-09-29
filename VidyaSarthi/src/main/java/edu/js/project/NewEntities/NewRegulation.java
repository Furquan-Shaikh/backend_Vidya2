package edu.js.project.NewEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewRegulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String name;
    @Column(unique = true)
    private String regulationId;
    @OneToMany(mappedBy = "regulations", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<NewSubject> newSubjects = new HashSet<>();
    @OneToMany (mappedBy = "regulations", cascade = CascadeType.ALL)

    private List<NewMaterial> material = new ArrayList<>();


}
