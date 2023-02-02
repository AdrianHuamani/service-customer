package com.bcp.customer.web;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.bcp.customer.service.impl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import com.bcp.customer.domain.Customer;
import com.bcp.customer.web.mapper.CustomerMapper;
import com.bcp.customer.web.model.CustomerModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customers")
@Tag(name = "CUSTOMER", description = "customer api controller")
public class CustomerController {

	private final CustomerServiceImpl customerService;

	private final CustomerMapper customerMapper;

	@Operation(summary = "Get all customers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the customers",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Customer.class)) }),
			@ApiResponse(responseCode = "204", description = "No content customers in database",
					content = @Content)
			 })
	@GetMapping
	public Mono<ResponseEntity<List<Customer>>> findAll(){
		log.info("Find All Customer Controller executed");
		return customerService.findAll()
				.collectList()
				.map( listCustomers ->{
					if (listCustomers.isEmpty()){
						return ResponseEntity.noContent().build();
					}else{
						return ResponseEntity.ok()
								.contentType(MediaType.APPLICATION_JSON)
								.body(listCustomers);
					}
				});
	}


	@GetMapping("/id/{id}")
	public Mono<ResponseEntity<Customer>> findById(@PathVariable String id){
		log.info("Find By Id Customer" + id);
		return customerService.findById(id)
				.map(customerEntity ->ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(customerEntity))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping("/documentCode/{documentCode}")
	public Mono<ResponseEntity<List<Customer>>> findByDocumentCode(@PathVariable String documentCode){
		log.info("Find by document Code executed");
		return customerService.findByDocumentCode(documentCode)
				.collectList()
				.map( listCustomers ->{
					if (listCustomers.isEmpty()){
						return ResponseEntity.noContent().build();
					}else{
						return ResponseEntity.ok()
								.contentType(MediaType.APPLICATION_JSON)
								.body(listCustomers);
					}
				});
	}

	@GetMapping("/internalCode/{internalCode}")
	public Mono<ResponseEntity<Customer>> findByInternalCode(@PathVariable String internalCode){
		log.info("Find by document Code executed");
		return customerService.findByInternalCode(internalCode)
				.map(customerEntity ->ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(customerEntity))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<Customer>> save(@Valid @RequestBody CustomerModel customerModel,
			final ServerHttpRequest req) {
		log.info("Save executed", customerModel);
		return customerService.save(customerMapper.modelToEntity(customerModel))
				.map(c->(Customer) c)
				.flatMap(customerEntity -> Mono.just(
						ResponseEntity
								.created(URI.create(req.getURI().toString().concat("/").concat(customerEntity.getId())))
								.contentType(MediaType.APPLICATION_JSON)
								.body(customerEntity)
						)
				);
	}



	@PutMapping("/{id}")
	public Mono<ResponseEntity<Customer>> update(@PathVariable("id") String id, @RequestBody CustomerModel customerModel,final ServerHttpRequest req) {
		log.info("Update Customer", customerModel);
		return customerService.update(id, customerMapper.modelToEntity(customerModel))
				.map(c -> ResponseEntity
						.created(URI.create(req.getURI().toString().concat("/").concat(c.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(c))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
		return customerService.findById(id).flatMap(
				e -> customerService.delete(e.getId()).thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

}
