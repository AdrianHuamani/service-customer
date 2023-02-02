package com.bcp.customer.service.impl;

import com.bcp.customer.domain.DocumentType;
import com.bcp.customer.repository.DocumentTypeRepository;
import com.bcp.customer.service.DocumentTypeService;
import com.bcp.customer.web.mapper.DocumentTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;
    
    private final DocumentTypeMapper documentTypeMapper;
    
    @Override
    public Flux<DocumentType> findAll() {
        log.debug("findAll executed");
        return documentTypeRepository.findAll();
    }

    @Override
    public Mono<DocumentType> findById(String id) {
        log.debug("findById executed {}", id);
        return documentTypeRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("delete executed {}", id);
        return documentTypeRepository.deleteById(id);
    }

    public Mono<Void> deleteByType(String type) {
        log.debug("delete executed {}", type);
        return documentTypeRepository.deleteByType(type);
    }

    @Override
    public Mono<DocumentType> save(DocumentType documentType) {
        log.debug("create executed {}", documentType);
        documentType.setUserCreationId(System.getProperty("user.name"));
        documentType.setCreationDate(LocalDateTime.now());
        return documentTypeRepository.save(documentType);
    }

    @Override
    public Mono<DocumentType> update(String id , DocumentType documentType) {
        log.debug("update executed {}:{}", id, documentType);
        return documentTypeRepository.findById(id)
                .flatMap(dbDocumentType -> {
                    dbDocumentType.setModifiedDate(LocalDateTime.now());
                    dbDocumentType.setUserUpdateId(System.getProperty("user.name"));
                    dbDocumentType.setType(documentType.getType());
                    dbDocumentType.setDescriptionType(documentType.getDescriptionType());
                    dbDocumentType.setExtension(documentType.getExtension());
                    dbDocumentType.setStatus(documentType.getStatus());
                    return documentTypeRepository.save(dbDocumentType);
                });
    }

    public Mono<DocumentType> findByDescriptionType (String descriptionType){
        log.info("find document by description :"+descriptionType);
        return documentTypeRepository.findByDescriptionType(descriptionType);
    }

    public Mono<DocumentType> findByType (String type){
        log.info("find by document Type");
        return documentTypeRepository.findByType(type);
    }


}
