package com.bcp.customer.repository;

import com.bcp.customer.domain.Customer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerRepository extends GenericRepository<Customer,String>{

     Flux<Customer> findByDocumentCode (String documentCode);

     Mono<Customer> findByInternalCode (String internalCode);

     Flux<Customer> findByNameAndLastName (String name, String lastName);
	
}
