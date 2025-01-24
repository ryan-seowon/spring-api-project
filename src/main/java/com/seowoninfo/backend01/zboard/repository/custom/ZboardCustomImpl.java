package com.seowoninfo.backend01.zboard.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seowoninfo.backend01.common.util.UtilCommon;
import com.seowoninfo.backend01.common.util.UtilDate;
import com.seowoninfo.backend01.common.util.UtilQueryDsl;
import com.seowoninfo.backend01.zboard.dto.ZboardResponseDto;
import com.seowoninfo.backend01.zboard.dto.ZboardSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.seowoninfo.backend01.zboard.entity.QZboard.zboard;
import static com.seowoninfo.backend01.zboard.entity.QZcomments.zcomments;
import static com.seowoninfo.backend01.zboard.entity.QZfile.zfile;

@RequiredArgsConstructor
public class ZboardCustomImpl implements ZboardCustom {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public Page<ZboardResponseDto> findBoardAll(ZboardSearchDto paramDto, Pageable pageable) {
		List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);
		
		List<ZboardResponseDto> query = queryFactory.select(
				Projections.bean(ZboardResponseDto.class
				, zboard.boardSeq
				, zboard.boardTitle
				, zboard.boardContents
				, zboard.viewCount
				, zboard.priorityYn
				, zboard.deletedYn
				, zboard.createdBy
				, zboard.createdDttm
				, zboard.modifiedBy
				, zboard.modifiedDttm
				, ExpressionUtils.as(
						select(zcomments.count())
						.from(zcomments)
						.where(zcomments.board.eq(zboard))
					, "commentCount")
				, ExpressionUtils.as(
						select(zfile.count())
								.from(zfile)
								.where(zfile.board.eq(zboard)), "fileCount")
			))
			.from(zboard)
			.where(allCondition(paramDto))
			.orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		
		JPAQuery<Long> countQuery = queryFactory
				.select(zboard.count())
				.from(zboard)
				.where(allCondition(paramDto));
		
		return PageableExecutionUtils.getPage(query, pageable, countQuery::fetchOne);
	}

	/**
	 * sort
	 * @param pageable
	 * @return
	 */
	private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
		List<OrderSpecifier> ORDERS = new ArrayList<>();
		if (UtilCommon.isNotEmpty(pageable.getSort())) {
			for (Sort.Order order : pageable.getSort()) {
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
				switch (order.getProperty()) {
					case "boardSeq":
						OrderSpecifier<?> orderId = UtilQueryDsl.getSortedColumn(direction, zboard, "boardSeq");
						ORDERS.add(orderId);
						break;
					default:
						break;
				}
			}
		}
		return ORDERS;
	}

	/**
	 * 검색조건
	 * @param paramDto
	 * @return
	 */
	private BooleanBuilder allCondition(ZboardSearchDto paramDto) {
		BooleanBuilder builder = new BooleanBuilder();
		return builder
				.and(createdDttmBetween(paramDto.getStartDate(), paramDto.getEndDate()))
				.and(titleContains(paramDto.getSearchValue()))
				.or(contentsContains(paramDto.getSearchValue()));
		
	}

	/**
	 * 기간조건
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private BooleanExpression createdDttmBetween(LocalDate startDate, LocalDate endDate) {
		if (UtilCommon.isEmpty(startDate) && UtilCommon.isEmpty(endDate)) {
			return null;
		}
		return zboard.createdDttm.between(UtilDate.startDatetime(startDate), UtilDate.endDatetime(endDate));
	}

	/**
	 * 제목포함
	 * @param searchValue
	 * @return
	 */
	private BooleanExpression titleContains(String searchValue) {
		return UtilCommon.isEmpty(searchValue) ? null : zboard.boardTitle.containsIgnoreCase(searchValue);
	}
	
	/**
	 * 내용포함
	 * @param searchValue
	 * @return
	 */
	private BooleanExpression contentsContains(String searchValue) {
		return UtilCommon.isEmpty(searchValue) ? null : zboard.boardContents.containsIgnoreCase(searchValue);
	}
	
	@Override
	public ZboardResponseDto findBoardbySeq(Long boardSeq) throws Exception {
		ZboardResponseDto query = queryFactory.select(
				Projections.bean(ZboardResponseDto.class
				, zboard.boardSeq
				, zboard.boardTitle
				, zboard.boardContents
				, zboard.viewCount
				, zboard.priorityYn
				, zboard.deletedYn
				, zboard.createdBy
				, zboard.createdDttm
				, zboard.modifiedBy
				, zboard.modifiedDttm
			))
		.from(zboard)
		.where(zboard.boardSeq.eq(boardSeq))
		.fetchOne();
		
		return query;
	}
}
