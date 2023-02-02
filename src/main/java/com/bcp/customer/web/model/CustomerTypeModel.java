package com.bcp.customer.web.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerTypeModel {
	
    @JsonIgnore
    private String id;

    @NotBlank(message="type cannot be null or empty")
    private String type;
    
    @NotBlank(message="profile cannot be null or empty")
    private String profile;

    @NotBlank(message="segmento cannot be null or empty")
    private String segment;

    @NotBlank(message="subsegmento cannot be null or empty")
    private String subsegment;

    @JsonIgnore
    private LocalDateTime creationDate;

    @JsonIgnore
    private LocalDateTime modifiedDate;

    @JsonIgnore
    private String userCreationId;
    
    @JsonIgnore
    private String userUpdateId;
    
}
