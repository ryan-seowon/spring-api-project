package com.seowoninfo.backend01.zboard.repository;

import com.seowoninfo.backend01.zboard.dto.ZcommentsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZcommentsRepositoryCustom {


	/**
	 * 댓글글리스트
	 * @param boardSeq
	 * @param pageable
	 * @return
	 */
	Page<ZcommentsResponseDto> findCommentsAll(Long boardSeq, Pageable pageable);
}
