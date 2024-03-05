package com.zavadskiy.candidate_management_service.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "speciality")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"candidates", "tests}"})
@EqualsAndHashCode(of = {"id", "name", "description"})
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @OneToMany(
            mappedBy = "speciality",
            fetch = FetchType.LAZY
    )
    private List<Test> tests;
    @ManyToMany(
            mappedBy = "specialities",
            fetch = FetchType.LAZY
    )
    private Set<Candidate> candidates;
}
