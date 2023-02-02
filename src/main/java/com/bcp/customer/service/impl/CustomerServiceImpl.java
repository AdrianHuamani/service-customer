package com.bcp.customer.service.impl;

import com.bcp.customer.domain.CustomerType;
import com.bcp.customer.exception.FunctionalException;
import com.bcp.customer.repository.CustomerTypeRepository;
import com.bcp.customer.repository.DocumentTypeRepository;
import com.bcp.customer.service.CustomerService;
import com.bcp.customer.web.mapper.CustomerTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bcp.customer.domain.Customer;
import com.bcp.customer.repository.CustomerRepository;
import com.bcp.customer.web.mapper.CustomerMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	    
	private final CustomerMapper customerMapper;

	private final CustomerTypeMapper customerTypeMapper;
	private final DocumentTypeRepository documentTypeRepository;

	private final CustomerTypeRepository customerTypeRepository;

	@Value("${CUSTOMER_DUPLICATE}")
	private String CUSTOMER_DUPLICATE;
	@Value("${CUSTOMER_NOT_FOUND}")
	private String CUSTOMER_NOT_FOUND;
	@Value("${CUSTOMER_TYPE_NOT_FOUND}")
	private String CUSTOMER_TYPE_NOT_FOUND;
	@Value("${DOCUMENT_TYPE_NOT_FOUND}")
	private String DOCUMENT_TYPE_NOT_FOUND;
	@Value("${DOCUMENT_CODE_IS_INCORRECT}")
	private String DOCUMENT_CODE_IS_INCORRECT;

	@Override
	public Flux<Customer> findByDocumentCode(String documentCode) {
		log.debug("findAll executed");
		return customerRepository.findAll();
	}



	@Override
	public Flux<Customer> findByNameAndLastName(String name, String lastName) {
		log.debug("findByNameAndLastName executed");
		return customerRepository.findByNameAndLastName(name,lastName);
	}

	@Override
	public Flux<Customer> findAll() {
		log.debug("findAll executed");
		return customerRepository.findAll();
	}

	@Override
	public Mono<Customer> findByInternalCode(String internalCode) {
		log.debug("findByInternalCode executed");
		return customerRepository.findByInternalCode(internalCode)
				.flatMap(customer->{
					return customerTypeRepository.findById(customer.getCustomerType().getId())
							.flatMap( x->{
								customer.setCustomerType(x);
								return
										Mono.just(customer);
							});
				});
	}
	@Override
	public Mono<Customer> findById(String id) {
		log.debug("findById executed {}", id);
		return customerRepository.findById(id)
				.flatMap(customer->{
					return customerTypeRepository.findById(customer.getCustomerType().getId())
							.flatMap( x->{
								customer.setCustomerType(x);
								return
								Mono.just(customer);
							});
				});
	}
	@Override
	public Mono<Void> delete(String id) {
		log.debug("delete executed {}", id);
		return customerRepository.deleteById(id);
	}

	public Mono<Object> checkCustomer(Customer customer){
		return documentTypeRepository.findByDescriptionType(customer.getDocumentDescription())
				.flatMap( documentTypeDb ->{
					if (customer.getDocumentCode().length()==documentTypeDb.getExtension()
						&& documentTypeDb.getStatus()){
						customer.setInternalCode(customer.getDocumentCode()+documentTypeDb.getType());
						return customerRepository.findByInternalCode(customer.getInternalCode())
								.flatMap(x->Mono.error(new FunctionalException(CUSTOMER_DUPLICATE)))
								.switchIfEmpty(checkCustomerType(customer));
					}else{
						return Mono.error(new FunctionalException(DOCUMENT_CODE_IS_INCORRECT));
					}
				})
				.switchIfEmpty(Mono.error(new FunctionalException(DOCUMENT_TYPE_NOT_FOUND)));
	}

	public Mono<Customer> checkCustomerType(Customer customer){
		return customerTypeRepository.findById(customer.getCustomerType().getId())
				.flatMap(y-> {
					//customer.setCustomerType(y);
					return
					Mono.just(customer);
				})
				.switchIfEmpty(Mono.error(new FunctionalException(CUSTOMER_TYPE_NOT_FOUND)));
	}

	@Override
	public Mono<Object> save(Customer customer){
		customer.setCreationDate(LocalDateTime.now());
		customer.setUserCreationId(System.getProperty("user.name"));
		return checkCustomer(customer)
				.flatMap(x ->customerRepository.save((Customer)x));
	}

	@Override
	public Mono<Customer> update(String id , Customer customer) {
		log.debug("update executed {}:{}", id, customer);
		customer.setModifiedDate(LocalDateTime.now());
		customer.setUserUpdateId(System.getProperty("user.name"));
		return customerRepository.findById(id)
				.flatMap(dbCustomer -> {
					return checkCustomerType(customer)
							.flatMap( customerValidate -> {
								dbCustomer.getCustomerType().setId(customerValidate.getCustomerType().getId());
								dbCustomer.setResident(customerValidate.getResident());
								dbCustomer.setCivilStatus(customerValidate.getCivilStatus());
								dbCustomer.setInstruction(customerValidate.getInstruction());
								dbCustomer.setCellPhones(customerValidate.getCellPhones());
								dbCustomer.setEmails(customerValidate.getEmails());
								dbCustomer.setLocation(customerValidate.getLocation());
								dbCustomer.setDirection(customerValidate.getDirection());
								dbCustomer.setEmployee(customerValidate.getEmployee());
								dbCustomer.setModifiedDate(LocalDateTime.now());
								dbCustomer.setUserUpdateId(System.getProperty("user.name"));
								dbCustomer.setCodeAgency(customerValidate.getCodeAgency());
								return customerRepository.save(dbCustomer);
									}
							);
				});
	}
}
