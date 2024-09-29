package com.kontial.cloud.service.cloudservice.exception;

import java.util.Map;

public class ValidationException extends Exception {
    final Map<String, String> data;

    public ValidationException(String message, Map<String, String> data) {
        super(message);
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }
}
