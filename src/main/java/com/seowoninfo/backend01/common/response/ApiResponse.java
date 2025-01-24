package com.seowoninfo.backend01.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * 컨트롤러의 모든 응답구조
 * @param <T>
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

	private String status;
	private String message;
	private T data;
	private T error;
	private Locale locale;
	private LocalDateTime timestamp;

	// S: 성공
	public static <T> ApiResponse<T> success() {
		return new ApiResponse<>(ResponseCode.SUCCESS.code(), null, ResponseCode.SUCCESS.message());
	}
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(ResponseCode.SUCCESS.code(), data, ResponseCode.SUCCESS.message());
	}
	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(ResponseCode.SUCCESS.code(), data, message);
	}
	// E: 성공
	
	// S: 실패
	public static <T> ApiResponse<T> fail(T data) {
		return new ApiResponse<>(ResponseCode.FAIL.code(), data, ResponseCode.FAIL.message());
	}
	public static <T> ApiResponse<T> fail(T data, String message, T error) {
		return new ApiResponse<>(ResponseCode.FAIL.code(), data, message, error);
	}
	// E: 실패

	// S: 오류
	public static <T> ApiResponse<T> error(T data, String message, T error) {
		return new ApiResponse<>(ResponseCode.FAIL.code(), data, message, error);
	}
	public static ApiResponse<?> error(String message) {
		return new ApiResponse<>(ResponseCode.FAIL.code(), null, message);
	}
	// E: 오류


	/**
	 * 성공
	 * @return
	 */
	public static ApiResponse<?> successWithNoContent() {
		return new ApiResponse<>(ResponseCode.SUCCESS.code(), null, null);
	}
	
	private ApiResponse(String status, T data, String message) {
		this.status = status;
		this.data = data;
		this.message = message;
		this.locale = LocaleContextHolder.getLocale();
		this.timestamp = LocalDateTime.now();
	}
	
	private ApiResponse(String status, T data, String message, T error) {
		this.status = status;
		this.data = data;
		this.message = message;
		this.error = error;
		this.locale = LocaleContextHolder.getLocale();
		this.timestamp = LocalDateTime.now();
	}
}
