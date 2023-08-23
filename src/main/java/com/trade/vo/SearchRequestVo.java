package com.trade.vo;

import com.trade.model.SearchCriteria;
import com.trade.model.SortObject;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SearchRequestVo {

    private Pagination pagination;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Pagination{
        private List<SortObject> sortParam;
        private FilterParam filterParam;
        private Integer page;
        private Integer size;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class  FilterParam{
        List<SearchCriteria> filterParam;
    }
}
