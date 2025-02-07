package com.seowoninfo.backend01.zzboard.service;


import com.seowoninfo.backend01.zboard.dto.ZboardResponseDto;
import com.seowoninfo.backend01.zboard.dto.ZboardSearchDto;
import com.seowoninfo.backend01.zzboard.mapper.ZzboardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 게시판
 */
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class ZzboardService {

	private final ZzboardMapper boardMapper;

	/**
	 * 게시판리스트
	 */
	public List<ZboardResponseDto> boardList(ZboardSearchDto zboardCreateDto, Pageable pageable) {
		List<ZboardResponseDto> result = boardMapper.boardList(zboardCreateDto);
		return result;
	}
}
