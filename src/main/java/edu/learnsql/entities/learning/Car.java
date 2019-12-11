package edu.learnsql.entities.learning;


import lombok.Data;

import java.util.Date;

@Data
public class Car {

    @DBTable(columnName ="id")
    private Integer id;

    @DBTable(columnName ="brand")
    private String brand;

    @DBTable(columnName ="model")
    private String model;

    @DBTable(columnName ="color")
    private String color;

    @DBTable(columnName ="releasedate")
    private Date releasedate;

    @DBTable(columnName ="number")
    private Integer number;

    @DBTable(columnName ="price")
    private Integer price;
}
