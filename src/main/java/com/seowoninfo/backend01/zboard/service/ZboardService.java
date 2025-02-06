package com.seowoninfo.backend01.zboard.service;


import com.seowoninfo.backend01.common.exception.CustomException;
import com.seowoninfo.backend01.common.response.ResponseCode;
import com.seowoninfo.backend01.common.util.UtilCommon;
import com.seowoninfo.backend01.common.util.UtilFile;
import com.seowoninfo.backend01.common.util.UtilMessage;
import com.seowoninfo.backend01.zboard.dto.*;
import com.seowoninfo.backend01.zboard.entity.Zboard;
import com.seowoninfo.backend01.zboard.entity.Zfile;
import com.seowoninfo.backend01.zboard.repository.ZboardRepository;
import com.seowoninfo.backend01.zboard.repository.ZfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 게시판
 */
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class ZboardService {

	private final UtilMessage utilMessage;
	private final ZboardRepository boardRepository;
	private final ZfileRepository fileRepository;
	
	@Value("${custom.format.dateStr}") private String FORMAT_DATESTR;
	@Value("${file.board.dir}") private String FILE_BOARD_DIR;
	@Value("${file.board.path}") private String FILE_BOARD_PATH;

	/**
	 * 게시판리스트
	 */
	public Page<ZboardResponseDto> boardList(ZboardSearchDto paramDto, Pageable pageable) {
		return boardRepository.findBoardAll(paramDto, pageable);
	}

	/**
	 * 게시판상세
	 */
	public ZboardResponseDto boardDetail(Long boardSeq) {
		Zboard zboard = boardRepository.findById(boardSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.get.nodata", null)));
		ZboardResponseDto result = ZboardResponseDto.toDto(zboard);

		// 파일정보추가
		List<ZfileResponseDto> 	files = new ArrayList<>();
		zboard.getFiles().forEach(file -> files.add(ZfileResponseDto.toDto(file)));
		result.setFiles(files);
		result.setFileCount((long) files.size());

		return result;
	}
	
	/**
	 * 게시판 등록
	 */
	@Transactional
	public ZboardResponseDto boardCreate(ZboardCreateDto paramDto, MultipartFile[] paramFiles) throws Exception{
		// S: 유효성검증
		// E: 유효성검증

		Zboard board = boardRepository.save(Zboard.toEntity(paramDto));

		// 파일업로드
		if(UtilCommon.isNotEmpty(paramFiles)) {
			ZfileCreateDto fileDto;
			StringBuilder systemFileName;
			for(MultipartFile file : paramFiles) {
//				log.debug("upload file: {}", file.getOriginalFilename());

				systemFileName = new StringBuilder();
				systemFileName.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_DATESTR)));
				systemFileName.append("_");
				systemFileName.append(UUID.randomUUID());
				systemFileName.append(".");
				systemFileName.append(FilenameUtils.getExtension(file.getOriginalFilename()));

				fileDto = new ZfileCreateDto();
				fileDto.setUploadPath(FILE_BOARD_PATH);
				fileDto.setOrgFileName(file.getOriginalFilename());
				fileDto.setSysFileName(systemFileName.toString());

				// 파일저장
				fileRepository.save(Zfile.toEntity(fileDto, board));

				// 물리 파일저장
				UtilFile.makeFolders(FILE_BOARD_DIR + FILE_BOARD_PATH);
				String filePath = FILE_BOARD_DIR + FILE_BOARD_PATH + File.separator + systemFileName;
				log.info("upload file: {}", filePath);
				file.transferTo(new File(filePath));
			}
		}

		return ZboardResponseDto.toDto(board);
	}
	
	/**
	 * 게시판 수정
	 */
	@Transactional
	public ZboardResponseDto boardModify(Long boardSeq, ZboardModifyDto paramDto, MultipartFile[] files) throws Exception{
		Zboard board = boardRepository.findById(boardSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.modify.nodata", null)));
		board.modifyZboard(paramDto);
		
		// UI상에서 삭제된 파일은 삭제처리해야함
		// 파일정보삭제
		if(UtilCommon.isNotEmpty(paramDto.getFileSeqs())) {
			for(Long item : paramDto.getFileSeqs()) {
				Optional<Zfile> zfileOptional = fileRepository.findById(item);
				if(zfileOptional.isPresent()){
					Zfile file = zfileOptional.get();
					fileRepository.delete(file);

					// 실제파일삭제
					Path filePath = Paths.get(FILE_BOARD_DIR + file.getUploadPath() + File.separator + file.getSysFileName());
					Files.deleteIfExists(filePath);
				}
			}
		}

		// 파일업로드
		if(UtilCommon.isNotEmpty(files)) {
			ZfileCreateDto fileDto;
			StringBuilder systemFileName;
			for(MultipartFile file : files) {
				log.debug("upload file: {}", file.getOriginalFilename());
				
				systemFileName = new StringBuilder();
				systemFileName.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_DATESTR)));
				systemFileName.append("_");
				systemFileName.append(UUID.randomUUID());
				systemFileName.append(".");
				systemFileName.append(FilenameUtils.getExtension(file.getOriginalFilename()));
				
				fileDto = new ZfileCreateDto();
				fileDto.setUploadPath(FILE_BOARD_PATH);
				fileDto.setOrgFileName(file.getOriginalFilename());
				fileDto.setSysFileName(systemFileName.toString());
				
				// 파일저장
				fileRepository.save(Zfile.toEntity(fileDto, board));
				
				// 물리 파일저장
				UtilFile.makeFolders(FILE_BOARD_DIR + FILE_BOARD_PATH);
				String filePath = FILE_BOARD_DIR + FILE_BOARD_PATH + File.separator + systemFileName;
				file.transferTo(new File(filePath));
			}
		}
		
		return ZboardResponseDto.toDto(board);
	}
	
	/**
	 * 게시판 삭제
	 */
	@Transactional
	public ZboardResponseDto boardDelete(Long boardSeq) {
		Zboard entity = boardRepository.findById(boardSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.delete.nodata", null)));
		boardRepository.delete(entity);
		return ZboardResponseDto.toDto(entity);
	}

}
