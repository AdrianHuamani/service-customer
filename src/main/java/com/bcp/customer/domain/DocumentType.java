package com.bcp.customer.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "documentTypes")
public class DocumentType {
	
    @Id
    private String id;

    @NotNull
    @Size(max=2)
    @Indexed(unique=true)
    private String type;

    @NotNull
    @Indexed(unique=true)
    private String descriptionType;
    
    @NotNull
    private Integer extension;

    @NotNull
    private Boolean status;

    @NotNull
    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    @NotNull
    private String userCreationId;

    private String userUpdateId;
    
}
