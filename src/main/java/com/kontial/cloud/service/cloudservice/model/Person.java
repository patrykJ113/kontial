package com.kontial.cloud.service.cloudservice.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Person {

    @Id
    private String id;
    private String name;
    private LocalDate birthday;

    // Default constructor (needed by JPA)
    public Person() {}

    // Constructor
    public Person(String id, String name, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}