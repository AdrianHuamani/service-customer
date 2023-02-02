package com.bcp.customer.service;

import com.bcp.customer.domain.DocumentType;
import reactor.core.publisher.Mono;

public interface DocumentTypeService extends GenericService<DocumentType,String> {

    Mono<DocumentType> save(DocumentType documentType);

     Mono<DocumentType> findByType (String type);

     Mono<DocumentType> findByDescriptionType (String descriptionType);

     Mono<Void> deleteByType (String type);
}
