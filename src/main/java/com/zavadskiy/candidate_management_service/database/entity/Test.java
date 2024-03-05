package com.zavadskiy.candidate_management_service.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "test")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"specialities", "testCandidates"})
@EqualsAndHashCode(of = {"id", "name", "description"})
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;
    @OneToMany(
            mappedBy = "test",
            fetch = FetchType.LAZY
    )
    private Set<CandidateTest> candidateTests;
}
