package com.trade.service;

import com.trade.constants.FilterConstants;
import com.trade.model.*;
import com.trade.model.entity.Trade;
import com.trade.repo.TradeRepo;
import com.trade.vo.SearchRequestVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceGraphQL {
    private final TradeRepo tradeRepo;

    public Flux<Trade> getTradesWithFilter(SearchRequestVo searchRequestVo) {

        final SearchRequestVo.Pagination pagination = searchRequestVo.getPagination();
        return tradeRepo.findByQuery(getFilterCriteria(pagination, false));
    }

    public Mono<ResultCount> count(SearchRequestVo searchRequestVo) {

        SearchRequestVo.Pagination pagination = searchRequestVo.getPagination();


        return tradeRepo.getCount(getFilterCriteria(pagination, true)).flatMap(count -> {
            final ResultCount resultCount = new ResultCount(count);
            return Mono.just(resultCount);
        });
    }

    public Mono<Trade> getTradesByTradeId(String tradeId) {
        return tradeRepo.findByTradeId(tradeId);
    }

    private String getFilterCriteria(final SearchRequestVo.Pagination pagination, final Boolean onlyFilterConditions) {
        final StringBuilder stringBuilder = new StringBuilder();

        SearchRequestVo.FilterParam filterParam = pagination.getFilterParam();
        List<SortObject> sortObjects = pagination.getSortParam();
        Integer page = pagination.getPage();
        Integer size = pagination.getSize();


        if (!ObjectUtils.isEmpty(filterParam) && !ObjectUtils.isEmpty(filterParam.getFilterParam())) {
            Iterator<SearchCriteria> iterator = filterParam.getFilterParam().iterator();

            while (iterator.hasNext()) {
                SearchCriteria searchCriteria = iterator.next();
                FilterType filterType = FilterType.value(searchCriteria.getFilterType());
                String filterExpression = searchCriteria.getFilterExpression();
                String key = searchCriteria.getKey();
                SearchCriteria.TextValue textValue = searchCriteria.getTextValue();
                String value = !ObjectUtils.isEmpty(textValue) ? textValue.getValue() : StringUtils.SPACE;
                if (StringUtils.isEmpty(key)) return null;

                switch (filterType) {
                    case NUMERIC -> {
                        SearchCriteria.NumberRangeValue numberRangeValue = searchCriteria.getNumberRangeValue();
                        BigDecimal start = numberRangeValue.getStart();
                        BigDecimal end = numberRangeValue.getEnd();

                        stringBuilder.append(FilterConstants.AND_CONDITION);
                        generateRangeConditions(stringBuilder, filterExpression, key, start, end);
                    }
                    case DATE -> {
                        SearchCriteria.DateRangeValue dateRangeValue = searchCriteria.getDateRangeValue();
                        LocalDate start = dateRangeValue.getStart();
                        LocalDate end = dateRangeValue.getEnd();

                        stringBuilder.append(FilterConstants.AND_CONDITION);
                        generateRangeConditions(stringBuilder, filterExpression, key, start, end);
                    }
                    case TEXT -> {

                        stringBuilder.append(FilterConstants.AND_CONDITION);
                        generateTextConditions(stringBuilder, filterExpression, key, value);
                    }
                    default -> {
                    }
                }
            }
        }

        generateSortConditions(stringBuilder, sortObjects, onlyFilterConditions);
        generatePaginationConditions(stringBuilder, size, page);

        return stringBuilder.toString();
    }


    private void appendCharacter(final StringBuilder stringBuilder, String character) {
        stringBuilder.append(character);
    }

    private void generateRangeConditions(final StringBuilder stringBuilder, final String filterExpression, final String key, final Object start, final Object end) {
        if (filterExpression != FilterConstants.IN_RANGE) {
            stringBuilder.append(key);
            appendCharacter(stringBuilder, StringUtils.SPACE);
            stringBuilder.append(filterExpression);
            appendCharacter(stringBuilder, StringUtils.SPACE);
            stringBuilder.append(start);
        } else {
            stringBuilder.append(start);
            appendCharacter(stringBuilder, StringUtils.SPACE);
            stringBuilder.append(FilterConstants.GREATER_THAN);
            appendCharacter(stringBuilder, StringUtils.SPACE);
            stringBuilder.append(key);
            appendCharacter(stringBuilder, StringUtils.SPACE);
            stringBuilder.append(FilterConstants.GREATER_THAN);
            appendCharacter(stringBuilder, StringUtils.SPACE);
            stringBuilder.append(end);
        }
    }

    private void generateTextConditions(final StringBuilder stringBuilder, final String filterExpression, final String key, final String value) {
        stringBuilder.append(key);
        appendCharacter(stringBuilder, StringUtils.SPACE);
        stringBuilder.append(filterExpression);
        appendCharacter(stringBuilder, StringUtils.SPACE);
        appendCharacter(stringBuilder, FilterConstants.SINGLE_QUOTATION);
        stringBuilder.append(value);
        appendCharacter(stringBuilder, FilterConstants.SINGLE_QUOTATION);
        appendCharacter(stringBuilder, StringUtils.SPACE);
    }

    private void generatePaginationConditions(final StringBuilder stringBuilder, final Integer size, final Integer page) {
        stringBuilder.append(FilterConstants.LIMIT_CONDITION);
        stringBuilder.append(size);
        appendCharacter(stringBuilder, StringUtils.SPACE);
        stringBuilder.append(FilterConstants.OFFSET_CONDITION);
        stringBuilder.append(page * size);
        appendCharacter(stringBuilder, StringUtils.SPACE);
    }

    private void generateSortConditions(final StringBuilder stringBuilder, List<SortObject> sortObjects, Boolean onlyFilterConditions) {

        if (!ObjectUtils.isEmpty(sortObjects) && !onlyFilterConditions) {
            stringBuilder.append(FilterConstants.ORDER_BY_CONDITION);
            Iterator<SortObject> sortObjectIterator = sortObjects.listIterator();

            while (sortObjectIterator.hasNext()) {
                SortObject sortObject = sortObjectIterator.next();

                stringBuilder.append(sortObject.getKey());
                appendCharacter(stringBuilder, StringUtils.SPACE);
                stringBuilder.append(sortObject.getSortType());
                appendCharacter(stringBuilder, StringUtils.SPACE);
                if (sortObjectIterator.hasNext()) {
                    stringBuilder.append(FilterConstants.COMMA);
                    appendCharacter(stringBuilder, StringUtils.SPACE);
                }
            }
        }
    }
}
