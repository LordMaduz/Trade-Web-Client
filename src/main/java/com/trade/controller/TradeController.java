package com.trade.controller;

import com.trade.model.ResultCount;
import com.trade.model.entity.Trade;
import com.trade.repo.TradeRepo;
import com.trade.service.TradeService;
import com.trade.service.TradeServiceGraphQL;
import com.trade.vo.SearchRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;
    private final TradeServiceGraphQL tradeServiceGraphQL;
    private final TradeRepo tradeRepo;

//    @GetMapping("/{id}")
//    public Flux<JsonNode> getAll(@PathVariable final String id) throws URISyntaxException {
//        return tradeService.getTrades(id);
//    }

    @GetMapping
    public Flux<Trade> getUnFiltered() {
        return tradeService.getUnFiltered();
    }

//    @GetMapping("/test")
//    public Mono<SearchResultVo> getAll(){
//        SearchRequestVo searchRequestVo  = new SearchRequestVo();
//        SearchRequestVo.Pagination pagination = new SearchRequestVo.Pagination();
//        pagination.setPage(0);
//        pagination.setSize(2);
//        searchRequestVo.setPagination(pagination);
//        return tradeServiceGraphQL.getTradesWithFilter(searchRequestVo);
//    }

    @GetMapping("/count")
    public Mono<ResultCount> getCount(){
        SearchRequestVo searchRequestVo  = new SearchRequestVo();
        SearchRequestVo.Pagination pagination = new SearchRequestVo.Pagination();
        pagination.setPage(0);
        pagination.setSize(3);
        searchRequestVo.setPagination(pagination);
        return tradeServiceGraphQL.count(searchRequestVo);
    }

//    @GetMapping("/filter/{fields}/{filters}")
//    public Flux<JsonNode> getTradeByQuery(@PathVariable final String fields, @PathVariable(required = false) final String filters) {
//        return tradeService.getTradeByQuery(fields, filters);
//    }

    @PostMapping()
    public Mono<Trade> addTrade(@RequestBody Trade trade) {
        return tradeService.addTrade(trade);
    }

    @GetMapping("/check")
    public Mono<Trade> check() {
        return tradeService.check();
    }
}

