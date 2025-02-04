package com.seowoninfo.backend01.zboard.entity;

import com.seowoninfo.backend01.common.entity.Base;
import com.seowoninfo.backend01.zboard.dto.ZcommentsCreateDto;
import com.seowoninfo.backend01.zboard.dto.ZcommentsModifyDto;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

/**
 * 댓글
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE ZCOMMENTS SET DELETED_YN = 'Y' WHERE COMMENTS_SEQ = ?")
@SQLRestriction("DELETED_YN = 'N'")
@DynamicInsert
@DynamicUpdate
@Table(name = "ZCOMMENTS")
public class Zcomments extends Base {

	@Comment("댓글순번")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMENTS_SEQ", nullable = false)
	private Long commentsSeq;
	
	@Comment("상위댓글순번")
	@Column(name = "PARENTS_COMMENTS_SEQ")
	private Long parentsCommentsSeq;

	@Comment("댓글내용")
	@Column(name = "COMMENTS", nullable = false, columnDefinition = "LONGTEXT")
	private String comments;
	
	@Comment("삭제여부")
	@ColumnDefault("'N'")
	@Column(name = "DELETED_YN", nullable = false, length = 1)
	private String deletedYn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOARD_SEQ")
	private Zboard board;

	// DTO -> Entity 로 변환
	public static Zcomments toEntity(ZcommentsCreateDto item, Zboard board) {
		return Zcomments.builder()
				.parentsCommentsSeq(item.getParentsCommentsSeq())
				.comments(item.getComments())
				.board(board)
				.build();
	}
	
	// 수정
	public void modifyComments(ZcommentsModifyDto item) {
		this.comments = item.getComments();
	}
}
