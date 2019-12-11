package edu.learnsql.entities.learning;

import lombok.Data;

@Data
public class Customer implements Entity{

    @DBTable(columnName ="id")
    private Integer id;

    @DBTable(columnName ="country")
    private String country;

    @DBTable(columnName ="postal_code")
    private String postal_code;

    @DBTable(columnName ="address")
    private String address;

    @DBTable(columnName ="city")
    private String city;

    @DBTable(columnName ="contact_name")
    private String contact_name;

    @DBTable(columnName ="customer_name")
    private String customer_name;

}
