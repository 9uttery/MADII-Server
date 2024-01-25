package com.guttery.madii.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorDetails {
    /**
     * 2000: Request 오류
     */
    BAD_REQUEST("2000", HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    NOT_FOUND("2001", HttpStatus.NOT_FOUND.value(), "요청한 URI를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED("2002", HttpStatus.METHOD_NOT_ALLOWED.value(), "해당 URI에서 지원하지 않는 HTTP Method입니다."),
    UNSUPPORTED_MEDIA_TYPE("2003", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "지원하지 않는 Media Type입니다."),
    TOO_MANY_REQUESTS("2004", HttpStatus.TOO_MANY_REQUESTS.value(), "요청 횟수가 너무 많습니다. 잠시 후 다시 시도해주세요."),
    INVALID_INPUT_PARAMETER("2005", HttpStatus.BAD_REQUEST.value(), "API Validation에 실패했습니다. Parameter를 확인해주세요."),
    INVALID_INPUT_BODY("2006", HttpStatus.BAD_REQUEST.value(), "API Validation에 실패했습니다. Body의 형식이 일치하는 지 확인해주세요."),
    INVALID_INPUT_ENUM("2007", HttpStatus.BAD_REQUEST.value(), "API Validation에 실패했습니다. Value 값으로 준 ENUM이 유효한 지 확인해주세요."),
    INVALID_FILE("2008", HttpStatus.BAD_REQUEST.value(), "유효하지 않은 파일입니다."),
    IMAGE_UPLOAD_FAILED("2009", HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미지 업로드에 실패했습니다."),
    IMAGE_DOWNLOAD_FAILED("2010", HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미지 다운로드에 실패했습니다."),

    /**
     * 3000: 권한 오류
     */
    UNAUTHORIZED("3000", HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 사용자입니다."),
    FORBIDDEN("3001", HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    INVALID_TOKEN("3002", HttpStatus.BAD_REQUEST.value(), "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("3003", HttpStatus.BAD_REQUEST.value(), "만료된 토큰입니다."),
    INVALID_REFRESH_TOKEN("3004", HttpStatus.BAD_REQUEST.value(), "유효하지 않은 리프레시 토큰입니다."),
    UNAUTHORIZED_ROLE("3005", HttpStatus.FORBIDDEN.value(), "인가되지 않는 사용자입니다."),
    EXPIRED_REFRESH_TOKEN("3006", HttpStatus.BAD_REQUEST.value(), "만료된 리프레시 토큰입니다."),
    INVALID_ID_TOKEN("3010", HttpStatus.BAD_REQUEST.value(), "유효하지 않은 ID 토큰입니다."),
    EXPIRED_ID_TOKEN("3011", HttpStatus.BAD_REQUEST.value(), "ID 토큰이 만료되었습니다."),
    INVALID_AUTHORIZATION_CODE("3012", HttpStatus.BAD_REQUEST.value(), "유효하지 않은 인가 코드입니다."),
    USER_AND_AGENCY_NOT_FOUND("3030", HttpStatus.NOT_FOUND.value(), "이메일로 사용자와 기관을 찾을 수 없습니다."),

    /**
     * 4000: 서버 오류
     */
    INTERNAL_SERVER_ERROR("4000", HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류가 발생했습니다."),
    JSON_PROCESSING_ERROR("4001", HttpStatus.INTERNAL_SERVER_ERROR.value(), "JSON 처리 중 오류가 발생했습니다."),

    USER_NOT_FOUND("U001", HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH("U002", HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    DUPLICATED_LOGIN_ID("U003", HttpStatus.BAD_REQUEST.value(), "중복된 로그인 아이디입니다."),
    KAKAO_KEY_SERVER_ERROR("U004", HttpStatus.INTERNAL_SERVER_ERROR.value(), "카카오 공개 키 서버와 통신 중 오류가 발생했습니다. 다시 시도해 주세요."),
    APPLE_KEY_SERVER_ERROR("U005", HttpStatus.INTERNAL_SERVER_ERROR.value(), "애플 공개 키 서버와 통신 중 오류가 발생했습니다. 다시 시도해 주세요."),

    JOY_NOT_FOUND("J001", HttpStatus.NOT_FOUND.value(), "소확행을 찾을 수 없습니다."),

    ALBUN_NOT_FOUND("A001", HttpStatus.NOT_FOUND.value(), "앨범을 찾을 수 없습니다."),
    ALREADY_EXIST_BOOKMARK("A002", HttpStatus.BAD_REQUEST.value(), "이미 저장한 앨범입니다."),

    ;

    private final String code;

    private final int status;

    private final String message;
}
