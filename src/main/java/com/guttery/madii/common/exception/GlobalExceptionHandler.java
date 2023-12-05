package com.guttery.madii.common.exception;

import com.guttery.madii.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ApiResponse<String> handleCustomException(final CustomException ex) {
        logExceptionInfo(ex);

        return ApiResponse.of(
                ex.getStatus(),
                ex.getCode(),
                ex.getMessage(),
                null);
    }

    // AccessDeniedException 처리 (403 Forbidden)
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<String> handleAccessDeniedException(final AccessDeniedException ex) {
        logExceptionInfo(ex);

        return ApiResponse.of(
                HttpStatus.FORBIDDEN.value(),
                ErrorDetails.FORBIDDEN.getCode(),
                ex.getMessage(),
                null);
    }

    // HttpRequestMethodNotSupportedException 처리 (405 Method Not Allowed)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<String> handleMethodNotAllowedException(final HttpRequestMethodNotSupportedException ex) {
        logExceptionInfo(ex);

        return ApiResponse.of(
                ErrorDetails.METHOD_NOT_ALLOWED.getStatus(),
                ErrorDetails.METHOD_NOT_ALLOWED.getCode(),
                ex.getMessage(),
                null);
    }

    // HttpMediaTypeNotSupportedException 처리 (415 Unsupported Media Type)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiResponse<String> handleUnsupportedMediaTypeException(final HttpMediaTypeNotSupportedException ex) {
        logExceptionInfo(ex);

        return ApiResponse.of(
                ErrorDetails.UNSUPPORTED_MEDIA_TYPE.getStatus(),
                ErrorDetails.UNSUPPORTED_MEDIA_TYPE.getCode(),
                ex.getMessage(),
                null);
    }

    // NullPointerException 처리 (500 Internal Server Error)
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<String> handleNullPointerException(final NullPointerException ex) {
        logExceptionInfo(ex);

        final String stackTrace = ExceptionUtils.getStackTrace(ex);
        return ApiResponse.of(
                ErrorDetails.INTERNAL_SERVER_ERROR.getStatus(),
                ErrorDetails.INTERNAL_SERVER_ERROR.getCode(),
                ex.getMessage(),
                stackTrace);
    }

    // ConstraintViolationException 처리 (400 Bad Request)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<String> handleConstraintViolationException(final ConstraintViolationException ex) {
        final String stackTrace = ExceptionUtils.getStackTrace(ex);
        return ApiResponse.of(
                ErrorDetails.INVALID_INPUT_PARAMETER.getStatus(),
                ErrorDetails.INVALID_INPUT_PARAMETER.getCode(),
                ex.getMessage(),
                stackTrace);
    }

    // MethodArgumentNotValidException 처리 (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        final String stackTrace = ExceptionUtils.getStackTrace(ex);
        final String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(StringBuilder::new, (sb, e) ->
                        sb.append(e.getDefaultMessage()).append("\n"), StringBuilder::append).toString();

        return ApiResponse.of(
                ErrorDetails.INVALID_INPUT_BODY.getStatus(),
                ErrorDetails.INVALID_INPUT_BODY.getCode(),
                message,
                stackTrace);
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex) {
        final String stackTrace = ExceptionUtils.getStackTrace(ex);
        return ApiResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorDetails.INTERNAL_SERVER_ERROR.getCode(),
                ex.getMessage(),
                stackTrace);
    }

    private void logExceptionInfo(final Exception ex) {
        if (ex instanceof final CustomException customException) {
            log.error("예외 발생 - Code: {}, Status: {}, Message: {}, Cause: {}",
                    customException.getCode(),
                    customException.getStatus(),
                    customException.getMessage(),
                    ExceptionUtils.getStackTrace(customException));
        } else {
            log.error("처리되지 않은 예외 발생 - Message: {}", ex.getMessage());
        }
    }
}
