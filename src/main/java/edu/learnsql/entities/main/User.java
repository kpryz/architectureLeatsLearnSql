package edu.learnsql.entities.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "learnsqlmain")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    @Email(message = "*Please enter a valid email adress!")
    @NotEmpty(message = "*Please provide an email! This field can not be empty!")
    private String email;

    @Column(name = "password")
    @Length(min = 3, message = "*Your password can not be less than 3 characters!")
    @NotEmpty(message = "*Please provide your password! This field can not be empty!")
    @Transient

    private String password;
    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name! This field can not be empty!")
    private String name;

    @Column(name = "active")
    private int active;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<SQLTask> userTask = new ArrayList<>();
}
