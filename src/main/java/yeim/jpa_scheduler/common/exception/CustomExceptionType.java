package yeim.jpa_scheduler.common.exception;

import org.springframework.http.HttpStatus;

public interface CustomExceptionType {

	HttpStatus getHttpStatus();

	String getCode();

	String getMessage();
}
