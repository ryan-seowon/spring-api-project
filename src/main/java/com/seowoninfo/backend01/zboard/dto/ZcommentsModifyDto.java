package com.seowoninfo.backend01.zboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 댓글
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ZcommentsModifyDto {
	@NotBlank
	private String comments;							// 댓글내용
}
