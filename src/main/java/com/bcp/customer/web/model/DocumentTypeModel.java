package com.bcp.customer.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class DocumentTypeModel {
	
    @JsonIgnore
    private String id;

    @NotBlank(message="type cannot be null or empty")
    @Size(max=2)
   // @Indexed(unique=true)
    private String type;

    @NotBlank(message="type cannot be null or empty")
   // @Indexed(unique=true)
    private String descriptionType;

    @Positive
    @NotNull
    private Integer extension;

    @NotNull
    private Boolean status;

    @JsonIgnore
    private LocalDateTime creationDate;

    @JsonIgnore
    private LocalDateTime modifiedDate;

    @JsonIgnore
    private String userCreationId;

    @JsonIgnore
    private String userUpdateId;

}
