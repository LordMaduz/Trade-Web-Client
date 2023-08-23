package com.trade.vo;

import com.trade.model.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SearchResultVo {

    private List<Trade> records;
    private Integer currentPage=0;
    private Integer numberOfElements;
    private Long totalElements;
}
