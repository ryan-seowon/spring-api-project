package com.seowoninfo.backend01.zboard.controller;

import com.seowoninfo.backend01.common.response.ApiResponse;
import com.seowoninfo.backend01.zboard.dto.*;
import com.seowoninfo.backend01.zboard.service.ZboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-23
 * Description :
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판 API")
@Slf4j
@RequestMapping("/sample/board")
public class ZboardController {

    private final ZboardService boardService;

    @Operation(summary = "게시판 리스트", description = "게시판 리스트")
    @GetMapping("")
    public ApiResponse<Map<String, Object>> boardList(
            ZboardSearchDto paramDto
            , @PageableDefault(size = 10, sort = "priorityYn", direction = Sort.Direction.DESC) Pageable pageable) throws Exception{
        return ApiResponse.success(boardService.boardList(paramDto, pageable));
    }

    @Operation(summary = "게시판 상세", description = "게시판 상세")
    @GetMapping("/{boardSeq}")
    public ApiResponse<Map<String, Object>> boardDetail(@PathVariable Long boardSeq) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", boardService.boardDetail(boardSeq));
        return ApiResponse.success(map);
    }

    @Operation(summary = "게시판 등록", description = "게시판을 등록한다")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<JSONObject> boardCreate(
            @Valid @RequestPart(name = "paramDto") ZboardCreateDto paramDto,
            @RequestPart(name = "paramFiles", required = false) MultipartFile[] paramFiles) throws Exception{
        log.debug("등록파람: {}", paramDto.toString());
        ZboardResponseDto result = boardService.boardCreate(paramDto, paramFiles);

        JSONObject json = new JSONObject();
        json.put("result", result);

        return ApiResponse.success(json);
    }

    @Operation(summary = "게시판 수정", description = "게시판을 수정한다")
    @PatchMapping(value = "/{boardSeq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<JSONObject> boardEdit(@PathVariable Long boardSeq,
                                              @Valid @RequestPart(name = "paramDto") ZboardModifyDto paramDto,
                                              @RequestPart(name = "files", required = false) MultipartFile[] files) throws Exception{
        ZboardResponseDto result = boardService.boardEdit(boardSeq, paramDto, files);

        JSONObject json = new JSONObject();
        json.put("result", result);

        return ApiResponse.success();
    }

    @Operation(summary = "게시판 삭제", description = "게시판을 삭제한다")
    @DeleteMapping("/{boardSeq}")
    public ApiResponse<JSONObject> boardCreate(@PathVariable Long boardSeq) throws Exception{
        ZboardResponseDto result = boardService.boardDelete(boardSeq);

        JSONObject json = new JSONObject();
        json.put("result", result);

        return ApiResponse.success();
    }


}
