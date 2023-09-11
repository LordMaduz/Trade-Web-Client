package com.trade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterExpression {

    GREATER_THAN("greaterThan", ">"),
    GREATER_THAN_OR_EQUAL("greaterThanOrEqual", ">="),
    LESS_THAN_OR_EQUAL("lessThanOrEqual", "<="),
    LESS_THAN("lessThan", "<"),
    EQUALS("equals", "="),
    NOT_EQUALS("notEquals", "!="),
    IN_RANGE("inRange", "IN"),
    BETWEEN("between", "BETWEEN"),
    CONTAINS("contains", "BETWEEN"),
    NOT_CONTAINS("notContains", "BETWEEN"),
    STARTS_WITH("startsWith", "BETWEEN"),
    ENDS_WITH("endsWith", "BETWEEN"),
    IS_NULL("isNull", "IS NULL"),
    IS_NOT_NULL("isNotNull", "IS NOT NULL"),
    IS_MISSING("isMissing", "IS MISSING"),
    IS_NOT_MISSING("isNotMissing", "IS NOT MISSING");



    private String expression;
    private String description;


}
