package com.bcp.customer.web;

import com.bcp.customer.domain.CustomerType;
import com.bcp.customer.domain.DocumentType;
import com.bcp.customer.service.DocumentTypeService;
import com.bcp.customer.web.mapper.DocumentTypeMapper;
import com.bcp.customer.web.model.CustomerTypeModel;
import com.bcp.customer.web.model.DocumentTypeModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/documentsTypes")
@Tag(name = "DOCUMENT TYPE", description = "document type controller")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    private final DocumentTypeMapper documentTypeMapper;

    @GetMapping
   public Mono<ResponseEntity<List<DocumentTypeModel>>> findAll(){
       log.info("Find All Customer Type Controller executed");
       return documentTypeService.findAll()
               .map(documentTypeMapper::entityToModel)
               .collectList()
               .map( listDocumentTypes ->{
                   if (listDocumentTypes.isEmpty()){
                       return ResponseEntity.noContent().build();
                   }else{
                        return ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(listDocumentTypes);
                   }
               });
   }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<DocumentTypeModel>> findById(@PathVariable("id") String id){
        log.info("Find by id executed");
        return documentTypeService.findById(id)
                .map(documentType -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(documentTypeMapper.entityToModel(documentType))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Mono<ResponseEntity<DocumentType>> save(@Valid @RequestBody DocumentTypeModel documentTypeModel,
                                                   final ServerHttpRequest req) {
        log.info("Save executed", documentTypeModel);
        return documentTypeService.save(documentTypeMapper.modelToEntity(documentTypeModel))
                .map(documentTypeEntity -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(documentTypeEntity.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(documentTypeEntity))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<DocumentTypeModel>> updateById(@PathVariable("id") String id
            , @Valid @RequestBody DocumentTypeModel documentTypeModel
            ,final ServerHttpRequest req) {
        log.info("updateById executed {}:{}", id, documentTypeModel);
        return documentTypeService.update(id, documentTypeMapper.modelToEntity(documentTypeModel))
                .map(documentType -> documentTypeMapper.entityToModel(documentType))
                .map(c -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") String id) {
        log.info("deleteById executed {}", id);
        return documentTypeService.findById(id)
                .flatMap(
                        e -> documentTypeService.delete(e.getId())
                                .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/type/{id}")
    public Mono<ResponseEntity<Void>> deleteByType(@PathVariable("id") String type) {
        log.info("deleteById executed {}", type);
        return documentTypeService.findByType(type)
                .flatMap(
                        e -> documentTypeService.deleteByType(e.getType())
                                .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
