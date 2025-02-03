package com.seowoninfo.backend01.zboard.repository;

import com.seowoninfo.backend01.zboard.dto.ZboardResponseDto;
import com.seowoninfo.backend01.zboard.dto.ZboardSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZboardRepositoryCustom {

	/**
	 * 게시글리스트
	 * @param paramDto
	 * @param pageable
	 * @return
	 */
	Page<ZboardResponseDto> findBoardAll(ZboardSearchDto paramDto, Pageable pageable);
	
	/**
	 * 게시글상세
	 * @param boardSeq
	 * @return
	 * @throws Exception
	 */
	ZboardResponseDto findBoardbySeq(Long boardSeq) throws Exception;
}
