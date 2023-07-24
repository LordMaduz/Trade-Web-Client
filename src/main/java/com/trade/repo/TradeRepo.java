package com.trade.repo;

import com.trade.model.entity.Trade;
import org.springframework.data.couchbase.repository.DynamicProxyable;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepo extends ReactiveCouchbaseRepository<Trade, String>, DynamicProxyable<TradeRepo> {
}