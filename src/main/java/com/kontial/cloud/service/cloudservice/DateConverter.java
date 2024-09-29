package com.kontial.cloud.service.cloudservice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter {
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public LocalDate convertToLocalDate(String birthdayStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            return LocalDate.parse(birthdayStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
