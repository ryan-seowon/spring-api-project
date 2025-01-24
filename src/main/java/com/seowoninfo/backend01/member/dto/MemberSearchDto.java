package com.seowoninfo.backend01.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 회원조회
 */
@Getter
@Setter
@ToString
public class MemberSearchDto{
	private String searchValue;
	private LocalDate startDate;
	private LocalDate endDate;
}
