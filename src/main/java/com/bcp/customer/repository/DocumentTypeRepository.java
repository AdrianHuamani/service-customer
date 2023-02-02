package com.bcp.customer.repository;

import com.bcp.customer.domain.DocumentType;
import reactor.core.publisher.Mono;

public interface DocumentTypeRepository extends GenericRepository<DocumentType,String> {

     Mono<DocumentType> findByType (String type);

     Mono<DocumentType> findByDescriptionType (String descriptionType);

     Mono<Void> deleteByType (String type);
}
