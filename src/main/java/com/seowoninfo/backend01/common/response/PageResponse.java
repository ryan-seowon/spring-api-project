package com.seowoninfo.backend01.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * 페이지정보
 * @param <T>
 */
@Getter
@AllArgsConstructor
public class PageResponse<T> {
	private long totalElements;		// 전체 갯수
	private int size;				// 페이지사이즈
	private int totalPages;			// 전체페이지갯수
	private int number;				// 페이지번호
	private int numberOfElements;	// 현재페이지의 요소 갯수
	private boolean first;			// 첫페이지인지
	private boolean last;			// 마지막페이지인지
	private boolean empty;			// 페이지가 비어있는지
	private Sort sort;				// 소트정보
//	private Pageable pageable;		// 페이지정보 (별필요없어보여 안내려줄거임)

	public static <T> PageResponse<T> pageInfo(Page<T> page){
		return new PageResponse<>(
				page.getTotalElements()
				, page.getSize()
				, page.getTotalPages()
				, page.getNumber()
				, page.getNumberOfElements()
				, page.isFirst()
				, page.isLast()
				, page.isEmpty()
				, page.getSort()
//				, page.getPageable()
		);
	}
}
