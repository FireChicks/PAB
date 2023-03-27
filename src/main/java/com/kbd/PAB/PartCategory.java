package com.kbd.PAB;

import jakarta.persistence.*;


@Entity(name = "partcategory")
@Table
public class PartCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="name", columnDefinition = "VARCHAR(20)")
    private String name;

    public String getName() {
        return name;
    }

}

