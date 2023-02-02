package com.bcp.customer.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.bcp.customer.domain.Customer;
import com.bcp.customer.web.model.CustomerModel;

@Mapper(componentModel= "spring")
public interface CustomerMapper {
	
	CustomerMapper INSTANCE= Mappers.getMapper(CustomerMapper.class);
	
	Customer modelToEntity(CustomerModel customerModel);
	
	CustomerModel entityToModel(Customer event);

	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget Customer entity, Customer updateEntity);
	
}
