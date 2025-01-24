package com.seowoninfo.backend01.common.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;

public class UtilQueryDsl {

	/**
	 * sort 유틸
	 * @param order
	 * @param parent
	 * @param fieldName
	 * @return
	 */
	public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
		Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
		return new OrderSpecifier(order, fieldPath);
	}
}
