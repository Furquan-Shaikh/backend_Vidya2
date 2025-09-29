package edu.js.project.NewEntities;

import edu.js.project.enums.BranchType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data

public class NewBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private BranchType branch;

    @ManyToMany(mappedBy = "branches")
    private Set<NewRegulation> regulationList = new HashSet<>();

    @OneToMany(mappedBy = "branch")
    private List<NewSubject> subjectList = new ArrayList<>();


}
