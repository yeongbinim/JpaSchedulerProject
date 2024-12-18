package yeim.jpa_scheduler.common.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import yeim.jpa_scheduler.common.exception.CustomExceptionType;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionType implements CustomExceptionType {

	AUTH_FAILED(HttpStatus.NOT_FOUND, "A01", "이메일 또는 비밀번호가 일치하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
