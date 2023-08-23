package com.trade.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.repo.GenericRepo;
import com.trade.util.WebClientUtil;
import com.trade.model.entity.Trade;
import com.trade.repo.TradeRepo;
import com.trade.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepo tradeRepo;


    private final ApplicationContext context;
    private final WebClientUtil webClientUtil;

    private final ObjectMapper objectMapper;

//    public Flux<JsonNode> getTrades(String id) throws URISyntaxException {
//        return getOutput(id, "Trade", "Read", tradeRepo);
//    }

    private Flux<JsonNode> getOutput(final String securityGroup, final String resourceCollection, final String operation, final GenericRepo genericRepo) throws URISyntaxException {
        return webClientUtil.getResponse(securityGroup, "Read", "Trade", "trade-services", null, null, "TRUE").bodyToMono(ResponseVo.class).flatMapMany(res -> {
            if(!res.getResult().isAllow()){
                return genericRepo.findByQuery(res.getResult().getData().get(), "");
            }
            else{
                return Flux.empty();
            }
        });
    }

    private String generateContext(Integer value) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("balance", value);
        return objectMapper.writeValueAsString(map);
    }

    @Transactional(rollbackFor = Exception.class)
    public Mono<Trade> check() {
        return context.getBean(TradeService.class).update();
    }

    @Transactional
    public Mono<Trade> update() {
        return tradeRepo.findById("0d8c8982-2d51-4621-80b6-33ced07fea00").flatMap(t -> {
            Trade trade = new Trade();
            trade.setTradeInfo(t.getTradeInfo());
            return tradeRepo.save(trade);
        }).doOnNext(t -> {
            context.getBean(TradeService.class).delete();
        });

    }


    @Transactional
    public void delete() {
//        tradeRepo.deleteById("0d8c8982-2d51-4621-80b6-33ced07fea00").subscribe(t-> {
//            System.out.println(t);
//        });
        throw new RuntimeException();
    }

    public Mono<Trade> addTrade(Trade trade) {
        return tradeRepo.save(trade);
    }

    public void deleteTrade(Trade trade) {
        tradeRepo.delete(trade);
    }

    public Mono<Trade> updateTrade(Trade trade) {
        return tradeRepo.save(trade);
    }


//    public Flux<JsonNode> getTradeByQuery(final String fields, final String filters) {
//
//        return tradeRepo.findByQuery(fields, filters);
//    }


    public Flux<Trade> getUnFiltered() {
        return tradeRepo.findAll();
    }

    private void updateReference(final AtomicReference<String> fieldsReference, final String value) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fieldsReference.get());
        stringBuilder.append(",");
        stringBuilder.append(value);
        fieldsReference.set(stringBuilder.toString());
    }
}
