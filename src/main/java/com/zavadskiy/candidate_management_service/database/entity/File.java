package com.zavadskiy.candidate_management_service.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;

@Entity
@Table(name = "file")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "data")
@EqualsAndHashCode(of = {"id", "name", "contentType", "size"})
public class File {
    @Id
    @UuidGenerator
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private Long size;
    @Lob
    @JdbcTypeCode(Types.VARBINARY)
    @Column(nullable = false)
    private byte[] data;
}
