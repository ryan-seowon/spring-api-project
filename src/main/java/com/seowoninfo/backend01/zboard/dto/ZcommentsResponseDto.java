package com.seowoninfo.backend01.zboard.dto;

import com.seowoninfo.backend01.zboard.entity.Zcomments;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 댓글
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZcommentsResponseDto {

	private Long commentsSeq;				// 댓글순번
	private Long parentsCommentsSeq;	// 상위댓글순번
	private Long boardSeq;						// 게시글순번
	private String comments;						// 댓글내용
	private String deletedYn;						// 삭제여부

	private String createdBy;
	private LocalDateTime createdDttm;
	private String modifiedBy;
	private LocalDateTime modifiedDttm;
	
	// Entity -> DTO 로 변환
	public static ZcommentsResponseDto toDto(Zcomments item) {
		return ZcommentsResponseDto.builder()
				.commentsSeq(item.getCommentsSeq())
				.parentsCommentsSeq(item.getParentsCommentsSeq())
				.comments(item.getComments())
				.deletedYn(item.getDeletedYn())
				.createdBy(item.getCreatedBy())
				.createdDttm(item.getCreatedDttm())
				.modifiedBy(item.getModifiedBy())
				.modifiedDttm(item.getModifiedDttm())
				.build();
	}
}
