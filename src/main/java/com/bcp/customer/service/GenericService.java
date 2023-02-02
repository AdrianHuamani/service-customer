package com.bcp.customer.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericService <T,ID>{

    Mono<T> update(ID id,T t);
    Flux<T> findAll();
    Mono<T> findById(ID id);
    Mono<Void> delete(ID id);

}
