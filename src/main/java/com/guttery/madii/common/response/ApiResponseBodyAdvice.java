package com.guttery.madii.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final int SUCCESS_STATUS = 200;
    private static final String SUCCESS_CODE = "OK";
    private static final String SUCCESS_MESSAGE = "API 요청이 성공했습니다.";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType); // String, Boolean 등의 기타 응답에 대해서는 공통 응답 형식 적용 X
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof final ApiResponse<?> apiResponse) {
            return apiResponse;
        }

        return ApiResponse.of(SUCCESS_STATUS, SUCCESS_CODE, SUCCESS_MESSAGE, body); // 200, OK, API 요청이 성공했습니다.
    }
}
