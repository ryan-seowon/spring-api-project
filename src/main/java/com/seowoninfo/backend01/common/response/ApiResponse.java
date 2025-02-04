package com.seowoninfo.backend01.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 컨트롤러의 성공 응답구조
 * @param <T>
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

	private Map<String, Object> header;
	private T data;

	public static <T> ApiResponse<T> success() {
		return new ApiResponse<>(null, ResponseCode.SUCCESS.message());
	}
	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(null, message);
	}
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(data, ResponseCode.SUCCESS.message());
	}
	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(data, message);
	}

	private ApiResponse(T data, String message) {
		this.data = data;
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("message", message);
		headerMap.put("locale", LocaleContextHolder.getLocale());
		headerMap.put("timestamp", LocalDateTime.now());
		this.header = headerMap;
	}
}
