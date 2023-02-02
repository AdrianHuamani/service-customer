package com.bcp.customer.service;

import com.bcp.customer.domain.CustomerType;
import reactor.core.publisher.Mono;


public interface CustomerTypeService extends GenericService<CustomerType,String>{
    Mono<CustomerType> save(CustomerType customerType);


}
