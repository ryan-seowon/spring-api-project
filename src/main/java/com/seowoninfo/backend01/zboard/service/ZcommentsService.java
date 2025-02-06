package com.seowoninfo.backend01.zboard.service;

import com.seowoninfo.backend01.common.exception.CustomException;
import com.seowoninfo.backend01.common.response.ResponseCode;
import com.seowoninfo.backend01.common.util.UtilCommon;
import com.seowoninfo.backend01.common.util.UtilMessage;
import com.seowoninfo.backend01.zboard.dto.ZcommentsCreateDto;
import com.seowoninfo.backend01.zboard.dto.ZcommentsModifyDto;
import com.seowoninfo.backend01.zboard.dto.ZcommentsResponseDto;
import com.seowoninfo.backend01.zboard.entity.Zboard;
import com.seowoninfo.backend01.zboard.entity.Zcomments;
import com.seowoninfo.backend01.zboard.repository.ZboardRepository;
import com.seowoninfo.backend01.zboard.repository.ZcommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final ZboardRepository boardRepository;

    /**
     * 댓글리스트
     */
    public Page<ZcommentsResponseDto> commentsList(Long boardSeq, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        List<ZcommentsResponseDto> resultContents = new ArrayList<>();
        Map<Long, ZcommentsResponseDto> imsiMap = new HashMap<>();

        // 댓글 부모 밑에 자식 붙이기
        Page<ZcommentsResponseDto> page = commentsRepository.findCommentsAll(boardSeq, pageable);
        List<ZcommentsResponseDto> imsiResultList = page.getContent();
        imsiResultList.forEach(item -> {
            imsiMap.put(item.getCommentsSeq(), item);
            if(UtilCommon.isEmpty(item.getParentsCommentsSeq())){
                resultContents.add(item);
            }else{
                imsiMap.get(item.getParentsCommentsSeq()).getReComments().add(item);
            }
        });

        Page<ZcommentsResponseDto> resultPage = new PageImpl<>(resultContents, pageable, resultContents.size());

        return page;
    }

    /**
     * 댓글 등록
     */
    @Transactional
    public ZcommentsResponseDto commentsCreate(ZcommentsCreateDto paramDto) {
        Zboard board = boardRepository.findByBoardSeq(paramDto.getBoardSeq());
        Zcomments entity = commentsRepository.save(Zcomments.toEntity(paramDto, board));
        return ZcommentsResponseDto.toDto(entity);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public ZcommentsResponseDto commentsModify(Long boardSeq, ZcommentsModifyDto paramDto) {
        Zcomments entity = commentsRepository.findById(boardSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.modify.nodata", null)));
        entity.modifyComments(paramDto);
        return ZcommentsResponseDto.toDto(entity);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public ZcommentsResponseDto commentsDelete(Long commentsSeq) {
        Zcomments entity = commentsRepository.findById(commentsSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.delete.nodata", null)));
        commentsRepository.delete(entity);
        return ZcommentsResponseDto.toDto(entity);
    }
}
