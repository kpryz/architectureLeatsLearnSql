package edu.learnsql.entities.learning;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {

    @DBTable(columnName ="id")
    private Integer id;

    @DBTable(columnName ="birth_date")
    private Date birth_date;

    @DBTable(columnName ="first_name")
    private String first_name;

    @DBTable(columnName ="last_name")
    private String last_name;

    @DBTable(columnName ="notes")
    private String notes;
}
