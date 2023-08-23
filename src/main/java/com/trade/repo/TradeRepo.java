package com.trade.repo;

import com.fasterxml.jackson.databind.JsonNode;
import com.trade.model.entity.Trade;
import org.springframework.data.couchbase.repository.DynamicProxyable;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TradeRepo  extends ReactiveCouchbaseRepository<Trade, String>{


    @Query("SELECT #{[0]}, META().id AS __id, META().cas AS __cas FROM #{#n1ql.bucket} WHERE #{#n1ql.filter} #{[1]} ")
    Flux<JsonNode> findByQuery(final String fields, final String filters);


    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} #{[0]} ")
    Flux<Trade> findByQuery(final String filters);

    @Query("SELECT count(*) as count FROM #{#n1ql.bucket} WHERE #{#n1ql.filter}")
    Mono<Integer> getCount(final String filters);

    Mono<Trade> findByTradeId(String id);
}