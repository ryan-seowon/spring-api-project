package com.seowoninfo.backend01.zboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class ZcommentsCreateDto {

	@Positive
	private Long boardSeq;						// 게시글순번
	private Long parentsCommentsSeq;	// 상위댓글순번
	@NotBlank
	private String comments;						// 댓글내용
}
