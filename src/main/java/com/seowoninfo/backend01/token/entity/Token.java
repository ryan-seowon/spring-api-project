package com.seowoninfo.backend01.token.entity;

import com.seowoninfo.backend01.common.entity.Base;
import com.seowoninfo.backend01.token.dto.TokenCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 리프레시토큰관리
 * 필드 추가등 학인필요
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TOKEN")
public class Token extends Base {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Comment("아이디")
	@Column(name = "MEMBER_ID", unique = true, nullable = false, length = 20)
	private String memberId;
	
	@Comment("리프레시토큰")
	@Column(name = "REFRESH_TOKEN", nullable = false, length = 300)
	private String refreshToken;
	
	@Comment("리프레시토큰만료일시")
	@Column(name = "REFRESH_TOKEN_EXPIRATION")
	private LocalDateTime refreshTokenExpiration;
}
