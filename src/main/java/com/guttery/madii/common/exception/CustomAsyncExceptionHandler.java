package com.guttery.madii.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(final Throwable ex, final Method method, final Object... params) {
        if (ex instanceof final CustomException customException) {
            log.error("비동기 예외 발생: Code: {}, Status: {}, Message: {}, Cause: {}",
                    customException.getCode(),
                    customException.getStatus(),
                    customException.getMessage(),
                    ExceptionUtils.getStackTrace(customException));
        } else {
            log.error("처리되지 않은 비동기 예외 발생: {}", ex.getMessage());
        }
    }

}
