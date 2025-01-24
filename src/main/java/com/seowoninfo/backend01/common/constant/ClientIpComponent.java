package com.seowoninfo.backend01.common.constant;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Slf4j
@Component
public class ClientIpComponent {

	private static final List<String> IP_HEADERS = List.of(
		"X-Forwarded-For",						// 클라이언트의 원 IP 주소를 나타내기 위한 일반적인 헤더, 여러 IP 주소가 쉼표로 구분되어 있을 수 있음
		"HTTP_FORWARDED",				// RFC 7239에 정의된 표준화된 포워드 헤더, 클라이언트 및 프록시 서버 정보를 포함
		"Proxy-Client-IP",							// 일부 프록시 서버에서 사용하는 헤더, 클라이언트 IP 주소를 포함
		"WL-Proxy-Client-IP",					// WebLogic 서버에서 사용하는 헤더, 클라이언트 IP 주소를 포함
		"HTTP_CLIENT_IP",						// HTTP 요청의 클라이언트 IP를 나타내는 헤더, 일부 프록시 서버에서 사용
		"HTTP_X_FORWARDED_FOR",// 클라이언트의 원 IP 주소를 나타내는 또 다른 헤더, X-Forwarded-For와 유사
		"X-RealIP",										// Nginx와 같은 일부 웹 서버에서 사용하는 헤더, 클라이언트의 원 IP 주소를 포함
		"X-Real-IP",									// Nginx와 같은 일부 웹 서버에서 사용하는 헤더, 클라이언트의 원 IP 주소를 포함 (대시 포함 버전)
		"REMOTE_ADDR"						// Java의 ServletRequest에서 제공하는 메서드로, 직접 연결된 클라이언트의 IP 주소를 반환
	);

	/**
	 * HTTP 요청의 헤더에서 실제 클라이언트 IP 주소를 추출합니다.
	 * 다수의 프록시를 거친 경우 마지막 클라이언트 IP를 반환합니다.
	 *
	 * @return 클라이언트의 실제 IP 주소
	 */
	public String getIpFromHeader() {
		log.info("클라이언트 IP 수집");

		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		log.info("리퀘스트: " + request);

		// IP_HEADERS 목록에서 클라이언트 IP 확인
		for (String ipHeader : IP_HEADERS) {
			String clientIp = request.getHeader(ipHeader);
			log.info("헤더 {}: {}", ipHeader, clientIp);
			if (StringUtil.notNullNorEmpty(clientIp) && !"unknown".equalsIgnoreCase(clientIp)) {
				log.info("헤더 {}: {}", ipHeader, clientIp);
				return clientIp;
			}
		}
		
		// 모든 헤더에서 IP를 찾지 못한 경우, 기본적으로 제공되는 원격 주소 반환
		String remoteAddr = request.getRemoteAddr();
		log.info("리모트 주소: " + remoteAddr);
		return remoteAddr;
	}
}
