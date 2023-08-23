package com.trade.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.trade.model.ResultCount;
import com.trade.model.TradeFilter;
import com.trade.model.entity.Trade;
import com.trade.service.TradeServiceGraphQL;
import com.trade.vo.SearchRequestVo;
import com.trade.vo.SearchResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class TradeGraphQLController {

    private final TradeServiceGraphQL tradeServiceGraphQL;


    @QueryMapping
    Flux<Trade> tradeWithFilter(@Argument SearchRequestVo searchRequestVo){
        return tradeServiceGraphQL.getTradesWithFilter(searchRequestVo);
    }

    @QueryMapping
    Mono<ResultCount> count(@Argument SearchRequestVo searchRequestVo){
        return tradeServiceGraphQL.count(searchRequestVo);
    }


    @QueryMapping
    Mono<Trade> tradeByTradeId(@Argument String tradeId){
        return tradeServiceGraphQL.getTradesByTradeId(tradeId);
    }
}
