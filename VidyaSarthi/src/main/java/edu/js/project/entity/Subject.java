package edu.js.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String subjectCode;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    @ToString.Exclude // Avoid circular dependency issues
    @EqualsAndHashCode.Exclude
    private Semester semesters ;

    // One Subject has Many Units
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Unit> units = new HashSet<>();
}
