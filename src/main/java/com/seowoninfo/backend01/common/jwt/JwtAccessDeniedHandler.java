package com.seowoninfo.backend01.common.jwt;


import com.seowoninfo.backend01.common.exception.GlobalExceptionHandler;
import com.seowoninfo.backend01.common.response.ResponseCode;
import com.seowoninfo.backend01.common.util.UtilMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final UtilMessage utilMessage;
	/**
	 * 인가 실패 관련 403 핸들링
	 * @param request ServletRequest 객체
	 * @param response ServletResponse 객체
	 * @param accessDeniedException 접근권한 거부 예외 정보
	 * @throws IOException IO 예외 가능성 처리
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		System.out.println("::::::::::::::::::::::::::::::JwtAccessDeniedHandler(인가실패)::::::::::::::::::::::::::::");
		GlobalExceptionHandler.filterExceptionHandler(response, HttpStatus.FORBIDDEN, ResponseCode.ACCESS_DENIED, utilMessage.getMessage("access.denied", null));
	}
}