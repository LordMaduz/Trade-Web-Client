package com.trade.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SearchCriteria {
    private String key;
    private String filterType;
    private String filterExpression;
    private TextValue textValue;
    private DateRangeValue dateRangeValue;
    private NumberRangeValue numberRangeValue;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class TextValue{
        private String value;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class DateRangeValue{
        private LocalDate start;
        private LocalDate end;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class NumberRangeValue{
        private BigDecimal start;
        private BigDecimal end;

    }
}
