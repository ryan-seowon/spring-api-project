package com.seowoninfo.backend01.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 커스텀 익셉션
 * 익셉션 강제발생 시킬 경우 주로 사용한다
 */
public class CustomException extends Exception {

	private static final long serialVersionUID = 4663380430591151694L;
	private final HttpStatus httpStatus;
	
	public CustomException(HttpStatus httpStatus, String message) {
//		super(exceptionClass.toString() + message);
		super(message);
		this.httpStatus = httpStatus;
	}
	
	public int getHttpStatusCode() {return httpStatus.value();}

	public String getHttpStatusType() {
		return httpStatus.getReasonPhrase();
	}
	
	public HttpStatus getHttpStatus() {return httpStatus;}
	
}
