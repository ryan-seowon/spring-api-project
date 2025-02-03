package com.seowoninfo.backend01.zboard.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seowoninfo.backend01.zboard.dto.ZcommentsResponseDto;
import com.seowoninfo.backend01.zboard.entity.QZcomments;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class ZcommentsRepositoryImpl implements ZcommentsRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public Page<ZcommentsResponseDto> findCommentsAll(Long boardSeq, Pageable pageable) {
		QZcomments cm = QZcomments.zcomments;
		
		List<ZcommentsResponseDto> query = queryFactory.select(
				Projections.bean(ZcommentsResponseDto.class
				, cm.commentsSeq
				, cm.comments
				, cm.createdBy
				, cm.createdDttm
				, cm.modifiedBy
				, cm.modifiedDttm
			))
			.from(cm)
//			.where(cm.boardSeq.eq(boardSeq))
			.orderBy(cm.commentsSeq.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		
		JPAQuery<Long> countQuery = queryFactory
				.select(cm.count())
				.from(cm)
//				.where(cm.boardSeq.eq(boardSeq))
				;
		
		return PageableExecutionUtils.getPage(query, pageable, countQuery::fetchOne);
	}
}
