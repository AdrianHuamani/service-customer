package com.bcp.customer.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.bcp.customer.domain.CustomerType;
import com.bcp.customer.repository.CustomerTypeRepository;
import com.bcp.customer.service.CustomerTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerTypeServiceImpl implements CustomerTypeService {

	private final CustomerTypeRepository customerTypeRepository;

	@Override
	public Flux<CustomerType> findAll() {
		log.debug("findAll executed");
		return customerTypeRepository.findAll();
	}

	@Override
	public Mono<CustomerType> findById(String id) {
		log.debug("findById executed {}", id);
		return customerTypeRepository.findById(id);
	}

	@Override
	public Mono<Void> delete(String id) {
		log.debug("delete executed {}", id);
		return customerTypeRepository.deleteById(id);
	}

	@Override
	public Mono<CustomerType> save(CustomerType customerType) {
		log.debug("create executed {}", customerType);
		customerType.setUserCreationId(System.getProperty("user.name"));
		customerType.setCreationDate(LocalDateTime.now());
		return customerTypeRepository.save(customerType);
	}

	@Override
	public Mono<CustomerType> update(String id , CustomerType customerType) {
		log.debug("update executed {}:{}", id, customerType);
		return customerTypeRepository.findById(id)
				.flatMap(dbCustomerType -> {
					dbCustomerType.setType(customerType.getType());
					dbCustomerType.setProfile(customerType.getProfile());
					dbCustomerType.setSegment(customerType.getSegment());
					dbCustomerType.setSubsegment(customerType.getSubsegment());
					dbCustomerType.setModifiedDate(LocalDateTime.now());
					dbCustomerType.setUserUpdateId(System.getProperty("user.name"));
					return customerTypeRepository.save(dbCustomerType);
				});
	}

}
