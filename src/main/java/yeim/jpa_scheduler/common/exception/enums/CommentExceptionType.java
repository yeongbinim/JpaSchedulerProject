package yeim.jpa_scheduler.common.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import yeim.jpa_scheduler.common.exception.CustomExceptionType;

@Getter
@RequiredArgsConstructor
public enum CommentExceptionType implements CustomExceptionType {

	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C01", "일치하는 댓글을 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
