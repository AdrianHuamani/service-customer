package com.bcp.customer.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.bcp.customer.domain.CustomerType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CustomerModel {

	@JsonIgnore
	private String id;
	
	@JsonIgnore
	private String internalCode;
	
	@NotBlank(message="documentDescription cannot be null or empty")
	private String documentDescription;
	
	@NotBlank(message="documentCode cannot be null or empty")
	private String documentCode;


	private CustomerType customerType;
	
	@NotBlank(message="name cannot be null or empty")
	private String name;
	
	private String lastName;
	
	private LocalDate birthDate;
	
	@NotBlank(message="birthDate cannot be null or empty")
	private String nationality;

	@NotBlank(message="gender cannot be null or empty")
	private String gender;
	
	private Boolean resident;
	
	private String civilStatus;
	
	private String instruction;
	
	private List<String> cellPhones;
	
	private List< @Email String> emails;
	
	private String location;
	
	private String direction;
	
	private Boolean employee;

	private String codeAgency;

	@JsonIgnore
	private LocalDateTime creationDate;

	@JsonIgnore
	private LocalDateTime modifiedDate;

	@JsonIgnore
	private String userCreationId;

	@JsonIgnore
	private String userUpdateId;

}
