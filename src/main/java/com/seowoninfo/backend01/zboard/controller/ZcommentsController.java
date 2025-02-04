package com.seowoninfo.backend01.zboard.controller;

import com.seowoninfo.backend01.common.response.ApiResponse;
import com.seowoninfo.backend01.zboard.dto.ZcommentsCreateDto;
import com.seowoninfo.backend01.zboard.dto.ZcommentsModifyDto;
import com.seowoninfo.backend01.zboard.dto.ZcommentsResponseDto;
import com.seowoninfo.backend01.zboard.service.ZcommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-24
 * Description :
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판 댓글 API")
@Slf4j
@RequestMapping("/sample/board/comments")
public class ZcommentsController {

    private final ZcommentsService zcommentsService;

    @Operation(summary = "댓글 리스트", description = "댓글 리스트")
    @GetMapping("/")
    public ApiResponse<Map<String, Object>> commentsList(
            @RequestParam(value = "boardSeq", required = true) Long boardSeq
            , @PageableDefault(size = 10, sort = "commentsSeq", direction = Sort.Direction.DESC)Pageable pageable) throws Exception{
        return ApiResponse.success(zcommentsService.commentsList(boardSeq, pageable));
    }

    @Operation(summary = "댓글 등록", description = "댓글을 등록한다")
    @PostMapping("/")
    public ApiResponse<Map<String, Object>> commentsCreate(@Valid @RequestBody ZcommentsCreateDto paramDto) throws Exception{
        log.debug("등록파람: {}", paramDto.toString());
        ZcommentsResponseDto result = zcommentsService.commentsCreate(paramDto);

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);

        return ApiResponse.success(map);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정한다")
    @PatchMapping("/{commentsSeq}")
    public ApiResponse<Map<String, Object>> scommentsModify(@PathVariable Long commentsSeq, @RequestBody ZcommentsModifyDto paramDto) throws Exception{
        ZcommentsResponseDto result = zcommentsService.commentsModify(commentsSeq, paramDto);

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);

        return ApiResponse.success(map);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제한다")
    @DeleteMapping("/{commentsSeq}")
    public ApiResponse<Map<String, Object>> scommentsCreate(@PathVariable Long commentsSeq) throws Exception{
        zcommentsService.commentsDelete(commentsSeq);
        return ApiResponse.success();
    }
}
