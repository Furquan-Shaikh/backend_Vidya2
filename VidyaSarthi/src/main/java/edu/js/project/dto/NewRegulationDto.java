package edu.js.project.dto;

import edu.js.project.NewEntities.NewMaterial;
import edu.js.project.NewEntities.NewSubject;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NewRegulationDto {

    int id;
    private String name;
    private String regulationId;
    private Set<NewSubject> newSubjects = new HashSet<>();
    private List<NewMaterial> material = new ArrayList<>();

}
