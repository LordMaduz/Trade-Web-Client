package com.trade.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TradeFilter {
    private List<FilterField> filter;
    private Integer limit;
    private Integer offset;
}
