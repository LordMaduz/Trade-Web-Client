package com.trade.controller;

import com.trade.model.entity.Trade;
import com.trade.service.TestTradeService;
import com.trade.service.TradeServiceGraphQL;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class TestTradeController {

    private final TestTradeService tradeServiceGraphQL;

//    @QueryMapping
//    Flux<Trade> tradeWithFilter(@Argument SearchRequestVo searchRequestVo){
//        return tradeServiceGraphQL.getTradesWithFilter(searchRequestVo);
//    }
//
//    @QueryMapping
//    Mono<SearchResultVo> filteredTradesWithCount(@Argument SearchRequestVo searchRequestVo){
//        return tradeServiceGraphQL.filteredTradesWithCount(searchRequestVo);
//    }
//
//    @QueryMapping
//    Mono<ResultCount> count(@Argument SearchRequestVo searchRequestVo){
//        return tradeServiceGraphQL.count(searchRequestVo);
//    }


    @QueryMapping
    Mono<Trade> tradeByTradeId(@Argument String tradeId){
        return tradeServiceGraphQL.getTradesByTradeId(tradeId);
    }

    @MutationMapping
    Mono<Trade> createTrade(@Argument Trade trade){
        return tradeServiceGraphQL.createTrade(trade);
    }
}
