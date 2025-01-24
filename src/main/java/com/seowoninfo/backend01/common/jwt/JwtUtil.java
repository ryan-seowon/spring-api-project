package com.seowoninfo.backend01.common.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;


/**
 * jwt버전 0.12.3 
 * 개발자 유미
 * https://www.youtube.com/watch?v=7B6KHSZN3jY
 */
@Component
public class JwtUtil {

	private final SecretKey secretKey;
	
	public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());	// 객체키 만듬
	}
	
	
	/**
	 * 아이디 가져오기
	 * @param token
	 * @return
	 */
	public String getMemberId(String token) {
		// 암호화된 토큰을 보안키를 적용해서 확인한다.
		// 특정 데이타를 가져온다
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberId", String.class);
	}

	/**
	 * Role 을 가져온다
	 * @param token
	 * @return
	 */
	public String getMeberRole(String token) {
		// 암호화된 토큰을 보안키를 적용해서 확인한다.
		// 특정 데이타를 가져온다
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberRole", String.class);
	}

	/**
	 * access인지, refresh토큰인지 구분
	 * @param token
	 * @return
	 */
	public String getTokenCategory(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("tokenCategory", String.class);
	}
	
	/**
	 * 토큰 만료 여부
	 * @param token
	 * @return
	 */
	public Boolean isExpired(String token) {
		// 암호화된 토큰을 보안키를 적용해서 확인한다.
		// 토큰 만료확인
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}
	
	
	/**
	 * 토큰 발행
	 * @param tokenCategory
	 * @param memberId
	 * @param memberRole
	 * @param expiredMs
	 * @return
	 */
	public String createJwt(String tokenCategory, String memberId, String memberRole, Long expiredMs) {
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.claim("tokenCategory", tokenCategory)	// access인지, refresh토큰인지 구분값
				.claim("memberId", memberId)
				.claim("memberRole", memberRole)
				.issuedAt(new Date(System.currentTimeMillis()))		// 발행일시
				.expiration(new Date(System.currentTimeMillis() + expiredMs))		// 만료일시(발행일시 + 유횩간)
				.signWith(secretKey)		// 보안키를 이용해 암호화
				.compact();
	}
	
	
}
