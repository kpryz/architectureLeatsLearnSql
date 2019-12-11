package edu.learnsql.entities.learning;

import lombok.Data;

import java.util.Date;

@Data
public class Order implements Entity{

    @DBTable(columnName ="id")
    private Integer id;

    @DBTable(columnName ="carID")
    private Integer carID;

    @DBTable(columnName ="customerID")
    private Integer customerID;

    @DBTable(columnName ="employeeID")
    private Integer employeeID;

    @DBTable(columnName ="date")
    private Date date;

    @DBTable(columnName ="sum")
    private Integer sum;
}
