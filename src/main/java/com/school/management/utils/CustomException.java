package com.school.management.utils;

public class CustomException extends Exception {
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    String errorMessage;

    public CustomException(int status, String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
