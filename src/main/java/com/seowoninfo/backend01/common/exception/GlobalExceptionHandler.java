package com.seowoninfo.backend01.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seowoninfo.backend01.common.constant.ConstantsStatic;
import com.seowoninfo.backend01.common.response.ApiResponse;
import com.seowoninfo.backend01.common.response.ResponseCode;
import com.seowoninfo.backend01.common.util.UtilCommon;
import com.seowoninfo.backend01.common.util.UtilMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 핸들러
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private final UtilMessage utilMessage;
	
	/**
	 * 글로벌 익셉션
	 * 일반적인 익셉션은 여기로 다 들어온다
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiResponse<?>> exceptionHandler(Exception e) {
		log.debug("GlobalExceptionHandler:Exception");
		log.debug(e.getMessage());
		for(StackTraceElement error : e.getStackTrace()) {log.debug(error.toString());}
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(e.getMessage()));
//		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(utilMessage.getMessage("exception.system", new String[] {"Exception"})));
	}

	/**
	 * 데이타베이스 익셉션
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = DataAccessException.class)
	public ResponseEntity<ApiResponse<?>> exceptionHandler(DataAccessException e) {
		log.debug("GlobalExceptionHandler:DataAccessException");
		log.debug(e.getMessage());
		for(StackTraceElement error : e.getStackTrace()) {log.debug(error.toString());}
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(e.getMessage()));
//		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(utilMessage.getMessage("exception.system", new String[] {"DataAccessException"})));
	}
	
	/**
	 * 쿼리 익셉션
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = SQLException.class)
	public ResponseEntity<ApiResponse<?>> exceptionHandler(SQLException e) {
		log.debug("GlobalExceptionHandler:SQLException");
		log.debug(e.getMessage());
		for(StackTraceElement error : e.getStackTrace()) {log.debug(error.toString());}
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(e.getMessage()));
//		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(utilMessage.getMessage("exception.system", new String[] {"SQLException"})));
	}
	
	/**
	 * 지원하지 않은 HTTP method 호출 할 경우 발생
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class) // HttpRequestMethodNotSupportedException 예외를 잡아서 처리
	protected ResponseEntity<ApiResponse<?>> exceptionHandler(HttpRequestMethodNotSupportedException e) {
		log.debug("GlobalExceptionHandler:HttpRequestMethodNotSupportedException");
		log.debug(e.getMessage());
		for(StackTraceElement error : e.getStackTrace()) {log.debug(error.toString());}
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error( e.getMessage()));
//		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(utilMessage.getMessage("exception.system", new String[] {"HttpRequestMethodNotSupportedException"})));
	}
	
	/**
	 * 404 not found
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ApiResponse<?>> exceptionHandler(NoHandlerFoundException e) {
		log.debug("GlobalExceptionHandler:NoHandlerFoundException");
		log.debug(e.getMessage());
		for(StackTraceElement error : e.getStackTrace()) {log.debug(error.toString());}
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(e.getMessage()));
//		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(utilMessage.getMessage("exception.system", new String[] {"NoHandlerFoundException"})));
	}
	
	/**
	 * 커스텀 익셉션
	 * 의도한 익셉션의 경우 여기로 들어온다
	 * throw new CustomException(ExceptionClass.PROVIDER, HttpStatus.FORBIDDEN, "접근금지");
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<ApiResponse<?>> exceptionHandler(CustomException e) {
		log.debug("GlobalExceptionHandler:CustomException");
		log.debug(e.getMessage());
		for(StackTraceElement error : e.getStackTrace()) {log.debug(error.toString());}
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(e.getMessage()));
//		return ResponseEntity.status(e.getHttpStatusCode()).body(ApiResponse.error(sb.toString()));
//		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(utilMessage.getMessage("exception.system", new String[] {"CustomException"})));
	}
	
	/**
	 * validation 익셉션 발생시
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> exceptionHandler(final MethodArgumentNotValidException e) {
		log.debug("GlobalExceptionHandler:MethodArgumentNotValidException");
		log.debug(e.getMessage());
		// @valid 어토테이션과 dto의 제약으로 발생된 오류
		Map<String, String> errors = new HashMap<String, String>();
		e.getBindingResult().getAllErrors().forEach((error)-> {
			errors.put(((FieldError) error).getField(), error.getDefaultMessage());
		});
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(null, ResponseCode.FAIL.message(), errors));
	}
	
	// 필터등에서 exception이 발생할 경우 advice 범위 밖이라 여기로 안들어옴. 데이타 가공 
	public static void filterExceptionHandler(HttpServletResponse response, String responseCode, String message) {
		log.debug("GlobalExceptionHandler:filterExceptionHandler");
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		try {
			
			Map<String, Object> responseBody = new HashMap<String, Object>();
			responseBody.put("status", UtilCommon.isEmpty(responseCode) ? ResponseCode.FAIL.code() : responseCode);
			responseBody.put("message", message);
			responseBody.put("data", null);
			responseBody.put("error", null);
			responseBody.put("locale", LocaleContextHolder.getLocale());
			responseBody.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern(ConstantsStatic.format_datetime)));
			
			PrintWriter writer = response.getWriter();
			writer.write(new ObjectMapper().writeValueAsString(responseBody));
			writer.flush();
			writer.close();
			
//			JSONObject responseBody = new JSONObject();
//			responseBody.put("status", UtilCommon.isEmpty(responseCode) ? ResponseCode.FAIL.code() : responseCode);
//			responseBody.put("message", message);
//			responseBody.put("data", null);
//			responseBody.put("locale", LocaleContextHolder.getLocale());
//			responseBody.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS")));
//			response.getWriter().write(responseBody.toJSONString());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}