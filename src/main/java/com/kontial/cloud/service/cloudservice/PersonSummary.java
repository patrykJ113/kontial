package com.kontial.cloud.service.cloudservice;

public class PersonSummary {
    private String id;
    private String name;
    private int year;

    public PersonSummary(String id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
}
