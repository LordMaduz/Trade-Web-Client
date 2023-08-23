package com.trade.repo;
import reactor.core.publisher.Flux;


public interface GenericRepo<T> {

    Flux<T> findByQuery(final String fields, final String filters);
}
