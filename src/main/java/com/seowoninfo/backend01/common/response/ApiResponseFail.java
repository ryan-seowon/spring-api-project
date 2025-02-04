package com.seowoninfo.backend01.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 컨트롤러의 모든 응답구조
 * @param <T>
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponseFail<T> {

	private String errorCode;
	private Map<String, Object> header;
	private T error;

	public static <T> ApiResponseFail<T> fail(ResponseCode responseCode, String message) {
		return new ApiResponseFail<>(responseCode, null, message);
	}

	public static <T> ApiResponseFail<T> fail(ResponseCode responseCode, T error, String message) {
		return new ApiResponseFail<>(responseCode, error, message);
	}

	private ApiResponseFail(ResponseCode responseCode, T error, String message) {
		this.errorCode = responseCode.code();
		this.error = error;
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("message", message);
		headerMap.put("locale", LocaleContextHolder.getLocale());
		headerMap.put("timestamp", LocalDateTime.now());
		this.header = headerMap;
	}
}
