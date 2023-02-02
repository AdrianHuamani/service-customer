package com.bcp.customer.service;

import com.bcp.customer.domain.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService  extends GenericService<Customer,String>{

     Flux<Customer> findByDocumentCode (String documentCode);

     Mono<Customer> findByInternalCode (String internalCode);

     Flux<Customer> findByNameAndLastName (String name, String lastName);

     Mono<Object> save(Customer customer);
}
