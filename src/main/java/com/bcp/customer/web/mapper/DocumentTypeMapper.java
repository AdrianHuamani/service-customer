package com.bcp.customer.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.bcp.customer.domain.DocumentType;
import com.bcp.customer.web.model.DocumentTypeModel;

@Mapper(componentModel= "spring")
public interface DocumentTypeMapper {
	
	DocumentTypeMapper INSTANCE= Mappers.getMapper(DocumentTypeMapper.class);
	
	DocumentType modelToEntity(DocumentTypeModel documentTypeModel);
	
	DocumentTypeModel entityToModel(DocumentType event);

	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget DocumentType entity, DocumentType updateEntity);
	
}
