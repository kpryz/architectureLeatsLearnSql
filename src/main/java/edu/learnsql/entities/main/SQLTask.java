package edu.learnsql.entities.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "task")
@Table(name = "sqlTask", schema = "learnsqlmain")
public class SQLTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private User user;

    private String precondition;

    private String query;

    private String postcondition;

    @ManyToOne
    private Complexity complexity;

    private Integer maxAttempts;

}
