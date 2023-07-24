package com.trade.controller;

import com.trade.model.entity.Trade;
import com.trade.vo.TradeVo;
import com.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @GetMapping("/{securityGroup}")
    public Mono<List<TradeVo>> getAll(@PathVariable final String securityGroup) throws URISyntaxException {
        return tradeService.getTrades(securityGroup);
    }

    @GetMapping
    public Flux<Trade> getUnFiltered(){
        return tradeService.getUnFiltered();
    }

    @PostMapping()
    public Mono<Trade> addTrade(@RequestBody Trade trade){
        return tradeService.addTrade(trade);
    }
}
