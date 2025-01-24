package com.seowoninfo.backend01.zboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판파일
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ZfileCreateDto {

	private String uploadPath;	// 업로드경로
	private String orgFileName;	// 원본파일명
	private String sysFileName;	// 실제파일명
}
