package edu.phystech.hw2.contact;

public class InvalidContactFieldException extends RuntimeException {
    private String fieldName;

    public String getFieldName() {
        return this.fieldName;
    }

    InvalidContactFieldException(String fieldName) {
        this.fieldName = fieldName;
    }
}
