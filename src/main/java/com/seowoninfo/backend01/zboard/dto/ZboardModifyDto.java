package com.seowoninfo.backend01.zboard.dto;

import com.seowoninfo.backend01.common.validation.YnCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ZboardModifyDto {

	@NotBlank
	@Size(min=1, max = 200)
	private String boardTitle;		// 게시글제목
	@NotBlank
	private String boardContents;	// 게시글내용
	@YnCode
	private String priorityYn;		// 중요여부
	private Long[] fileSeqs;		// 파일순번
}
