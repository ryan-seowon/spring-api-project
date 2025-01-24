package com.seowoninfo.backend01.token.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TokenCreateDto {

	@NotBlank
	@Size(max = 20)
	private String memberId;
	
	@NotBlank
	@Size(max = 200)
	private String refreshToken;
	
	@Size(max = 100)
	private LocalDateTime refreshTokenExpiration;
	
}
