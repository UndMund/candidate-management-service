package com.zavadskiy.candidate_management_service.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Entity
@Table(name = "candidate_test")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
@FieldNameConstants
public class CandidateTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;
    @Column(nullable = false)
    private Integer grade;
    @Column(nullable = false)
    private LocalDate passedAt;
}
