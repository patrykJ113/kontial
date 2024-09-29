package com.kontial.cloud.service.cloudservice.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PersonDTO {
    private String id;
    private String name;
    private String birthday;
    private static final String DATE_FORMAT = "dd-MM-yyyy";

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public LocalDate getLocalDateBirthday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            LocalDate date = LocalDate.parse(birthday, formatter);
            return date;
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}