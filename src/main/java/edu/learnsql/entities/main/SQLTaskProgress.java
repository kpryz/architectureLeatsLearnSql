package edu.learnsql.entities.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "task")
@Table(name = "sqlTaskProgress", schema = "learnsqlmain")
public class SQLTaskProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private SQLTask sqlTask;

    @Enumerated(EnumType.STRING)
    private ProgresStatus query;

    private LocalDateTime lastModified;

    private Integer attempts;
}