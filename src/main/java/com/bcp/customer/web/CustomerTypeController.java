package com.bcp.customer.web;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import com.bcp.customer.service.CustomerTypeService;
import com.bcp.customer.web.mapper.CustomerTypeMapper;
import com.bcp.customer.web.model.CustomerTypeModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customersTypes")
@Tag(name = "CUSTOMER TYPE", description = "customer type api controller")
public class CustomerTypeController {

    private final CustomerTypeService customerTypeService;

    private final CustomerTypeMapper customerTypeMapper;


    @GetMapping
    public Mono<ResponseEntity<List<CustomerTypeModel>>> findAll(){
        log.info("Find All Customer Type Controller executed");
        return customerTypeService.findAll()
                .map(customerTypeMapper::entityToModel)
                .collectList()
                .map( listCustomerTypes ->{
                    if (listCustomerTypes.isEmpty()){
                        return ResponseEntity.noContent().build();
                    }else{
                        return ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(listCustomerTypes);
                    }
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerTypeModel>> findById(@PathVariable String id) {
        log.info("Find By Id Customer" + id);
        return customerTypeService.findById(id)
                .map(customerType -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customerTypeMapper.entityToModel(customerType))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CustomerTypeModel>> save(@Valid @RequestBody CustomerTypeModel customerTypeModel,
                                                   final ServerHttpRequest req) {
        log.info("Save executed", customerTypeModel);
        return customerTypeService.save(customerTypeMapper.modelToEntity(customerTypeModel))
                .map(customerTypeMapper::entityToModel)
                .map(customerEntity -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(customerEntity.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customerEntity))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerTypeModel>> update(@PathVariable("id") String id
            , @Valid @RequestBody CustomerTypeModel customerTypeModel
            ,final ServerHttpRequest req) {
        log.info("updateById executed {}:{}", id, customerTypeModel);
        return customerTypeService.update(id, customerTypeMapper.modelToEntity(customerTypeModel))
                .map(customerType -> customerTypeMapper.entityToModel(customerType))
                .map(c -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        log.info("deleteById executed {}", id);
        return customerTypeService.findById(id)
                .flatMap(
                        e -> customerTypeService.delete(e.getId())
                                .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
