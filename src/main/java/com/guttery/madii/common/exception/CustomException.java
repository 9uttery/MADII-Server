package com.guttery.madii.common.exception;

public class CustomException extends RuntimeException{
    private final ErrorDetails errorDetails;

    private CustomException(ErrorDetails errorDetails) {
        super(errorDetails.getMessage());
        this.errorDetails = errorDetails;
    }

    public static CustomException of(ErrorDetails errorDetails) {
        final CustomException customException = new CustomException(errorDetails);
        SentryCapturingHelper.captureException(customException);
        return customException;
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
