package com.zavadskiy.candidate_management_service.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "candidate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"specialities", "testCandidates"})
@EqualsAndHashCode(of = {"id", "firstName", "lastName", "patronymic", "description"})
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String patronymic;
    @Column(nullable = false)
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "photo_id",
            referencedColumnName = "id",
            nullable = false,
            unique = true
    )
    private File photo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "cv_id",
            referencedColumnName = "id",
            nullable = false,
            unique = true
    )
    private File cv;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "candidate_speciality",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private Set<Speciality> specialities;
    @OneToMany(
            mappedBy = "candidate",
            fetch = FetchType.LAZY
    )
    private Set<CandidateTest> candidateTests;
}
