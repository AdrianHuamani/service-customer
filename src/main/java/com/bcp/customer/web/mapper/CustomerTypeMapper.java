package com.bcp.customer.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.bcp.customer.domain.CustomerType;
import com.bcp.customer.web.model.CustomerTypeModel;

@Mapper(componentModel= "spring")
public interface CustomerTypeMapper {
	
	CustomerTypeMapper INSTANCE= Mappers.getMapper(CustomerTypeMapper.class);
	
	CustomerType modelToEntity(CustomerTypeModel customerTypeModel);
	
	CustomerTypeModel entityToModel(CustomerType event);

	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget CustomerType entity, CustomerType updateEntity);
	
}
