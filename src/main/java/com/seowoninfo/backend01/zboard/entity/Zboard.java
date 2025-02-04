package com.seowoninfo.backend01.zboard.entity;

import com.seowoninfo.backend01.common.entity.Base;
import com.seowoninfo.backend01.zboard.dto.ZboardCreateDto;
import com.seowoninfo.backend01.zboard.dto.ZboardModifyDto;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 샘플게시판
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE ZBOARD SET DELETED_YN = 'Y' WHERE BOARD_SEQ = ?")
@SQLRestriction("DELETED_YN = 'N'")
@DynamicInsert
@DynamicUpdate
@Table(name = "ZBOARD")
public class Zboard extends Base {

    @Comment("게시글순번")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_SEQ", nullable = false)
    private Long boardSeq;

    @Comment("게시글제목")
    @Column(name = "BOARD_TITLE", nullable = false, length = 200)
    private String boardTitle;

    @Comment("게시글내용")
    @Column(name = "BOARD_CONTENTS", nullable = false, columnDefinition = "LONGTEXT")
    private String boardContents;

    @Comment("조회수")
    @ColumnDefault("0")
    @Column(name = "VIEW_COUNT")
    private Long viewCount;

    @Comment("중요여부")
    @ColumnDefault("'N'")
    @Column(name = "PRIORITY_YN", nullable = false, length = 1)
    private String priorityYn;

    @Comment("삭제여부")
    @ColumnDefault("'N'")
    @Column(name = "DELETED_YN", nullable = false, length = 1)
    private String deletedYn;

    @OneToMany(mappedBy = "board")
    private List<Zcomments> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Zfile> files = new ArrayList<>();

    // DTO -> Entity 로 변환
    public static Zboard toEntity(ZboardCreateDto item) {
        return Zboard.builder()
                .boardTitle(item.getBoardTitle())
                .boardContents(item.getBoardContents())
                .priorityYn(item.getPriorityYn())
                .build();
    }

    // 수정
    public void modifyZboard(ZboardModifyDto item) {
        this.boardTitle = item.getBoardTitle();
        this.boardContents = item.getBoardContents();
        this.priorityYn = item.getPriorityYn();
    }

    // 조회수추가
    public void modifyViewCount(Long boardSLong) {
        this.viewCount = this.viewCount++;
    }
}