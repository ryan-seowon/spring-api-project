package com.seowoninfo.backend01.zboard.service;

import com.seowoninfo.backend01.common.exception.CustomException;
import com.seowoninfo.backend01.common.response.PageResponse;
import com.seowoninfo.backend01.common.response.ResponseCode;
import com.seowoninfo.backend01.common.util.UtilMessage;
import com.seowoninfo.backend01.zboard.dto.ZcommentsCreateDto;
import com.seowoninfo.backend01.zboard.dto.ZcommentsModifyDto;
import com.seowoninfo.backend01.zboard.dto.ZcommentsResponseDto;
import com.seowoninfo.backend01.zboard.entity.Zcomments;
import com.seowoninfo.backend01.zboard.repository.ZcommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-24
 * Description :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ZcommentsService {

    private final UtilMessage utilMessage;
    private final ZcommentsRepository commentsRepository;

    /**
     * 댓글리스트
     * @param boardSeq
     * @param pageable
     * @return
     * @throws Exception
     */
    public Map<String, Object> commentsList(Long boardSeq, Pageable pageable) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Page<ZcommentsResponseDto> page = commentsRepository.findCommentsAll(boardSeq, pageable);
        List<ZcommentsResponseDto> resultList = page.getContent();
        map.put("pageInfo", PageResponse.pageInfo(page));
        map.put("resultList", resultList);
        return map;
    }


    /**
     * 댓글 등록
     * @param paramDto
     * @return
     */
    @Transactional
    public ZcommentsResponseDto commentsCreate(ZcommentsCreateDto paramDto) throws Exception{
        Zcomments entity = commentsRepository.save(Zcomments.toEntity(paramDto));
        return ZcommentsResponseDto.toDto(entity);
    }

    /**
     * 댓글 수정
     * @param paramDto
     * @return
     */
    @Transactional
    public ZcommentsResponseDto commentsEdit(Long boardSeq, ZcommentsModifyDto paramDto) throws Exception{
//        Zcomments entity = commentsRepository.findById(boardSeq).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, utilMessage.getMessage("exception.edit.nodata", null)));
        Zcomments entity = commentsRepository.findById(boardSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.edit.nodata", null)));
        entity.modifyComments(paramDto);
        return ZcommentsResponseDto.toDto(entity);
    }

    /**
     * 댓글 삭제
     * @param boardSeq
     * @return
     */
    @Transactional
    public ZcommentsResponseDto commentsDelete(Long boardSeq) throws Exception{
//        Zcomments entity = commentsRepository.findById(boardSeq).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, utilMessage.getMessage("exception.delete.nodata", null)));
        Zcomments entity = commentsRepository.findById(boardSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.delete.nodata", null)));
        commentsRepository.delete(entity);
        return ZcommentsResponseDto.toDto(entity);
    }
}
