package com.guttery.madii.common.exception;

public class CustomException extends RuntimeException{
    private final ErrorDetails errorDetails;

    private CustomException(ErrorDetails errorDetails) {
        super(errorDetails.getMessage());
        this.errorDetails = errorDetails;
    }

    public static CustomException of(ErrorDetails errorDetails) {
        return new CustomException(errorDetails);
    }

    public int getErrorDetailsStatus() {
        return this.errorDetails.getStatus();
    }

    public String getErrorDetailsCode() {
        return this.errorDetails.getCode();
    }

    public String getErrorDetailsMessage() {
        return this.errorDetails.getMessage();
    }
}
