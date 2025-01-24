package com.seowoninfo.backend01.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 프론트 서버와 API 서버의 도매인정보가 서로 사맞디 아니하여
 * CORS (Cross-Origin Resource Sharing)는 브라우저가 
 * 프론트엔드 JavaScript 코드가 교차 출처(cross-origin)에 대한 
 * 응답에 접근하는 것을 차단하는지 여부를 결정하는 HTTP headers 전송으로 이루어진 시스템입니다
 */
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

	@Value("${server.host.front}") String HOST_FRONT;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 모든 컨트롤러 경로에 대해서 프런트 도매인정보 넣어주면됨(프런트서버 별도있을경우)
		registry.addMapping("/**").allowedOrigins(HOST_FRONT);
	}
	
}
