package com.seowoninfo.backend01.common.response;

/**
 * 응답코드
 */
public enum ResponseCode {
	SUCCESS("0000", "성공")
	, FAIL_TOKEN_EXPIRED("9981", "토큰 유효기간 만료")
	, FAIL_TOKEN_INVALID("9982", "유효하지 않은 토큰")
	, FAIL_TOKEN_MALFORMED("9983", "손상된 토큰")
	, FAIL("9999", "실패")
	;
	
	private final String code;
	private final String message;

	ResponseCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String code() {
		return code;
	}
	public String message() {
		return message;
	}
}
