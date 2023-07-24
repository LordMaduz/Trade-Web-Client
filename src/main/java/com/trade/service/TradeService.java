package com.trade.service;

import com.trade.util.WebClientUtil;
import com.trade.vo.ResponseVo;
import com.trade.vo.TradeVo;
import com.trade.model.entity.Trade;
import com.trade.repo.TradeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepo tradeRepo;
    private final WebClientUtil webClientUtil;

    public Mono<List<TradeVo>> getTrades(String securityGroup) throws URISyntaxException {
        Mono<List<Trade>> tradeMono = tradeRepo.findAll().collectList();

        Mono<ResponseVo> authMono = webClientUtil.getResponse(
                securityGroup, "ViewTrade", "TradeCollection",
                "trade-service").bodyToMono(ResponseVo.class);


        List<TradeVo> tradeVoList = new ArrayList<>();
        return tradeMono.flatMap(list -> {
            return authMono.flatMap(responseVo -> {
                list.forEach(element -> {
                    TradeVo v0 = new TradeVo();
                    v0.setTradeId(element.getTradeId());
                    if (responseVo.getResult().isAllow()) {
                        v0.setTradeInfo(element.getTradeInfo());
                    }
                    tradeVoList.add(v0);
                });
                return Mono.fromCallable(() ->
                        tradeVoList
                );
            });
        });
    }

    public Mono<Trade> addTrade(Trade trade){
        return tradeRepo.save(trade);
    }

    public Flux<Trade> getUnFiltered(){
        return tradeRepo.findAll();
    }
}
