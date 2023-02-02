package com.bcp.customer.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "customersTypes")
//@CompoundIndex(def = "{'type': 1, 'profile': 1, 'segment':1, 'subsegment':1,}", unique = true)
public class CustomerType {
	
    @Id
    private String id;

    @NotNull
    private String type;

    @NotNull
    private String profile;

    @NotNull
    private String segment;

    @NotNull
    private String subsegment;

    @NotNull
    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    @NotNull
    private String userCreationId;
    
    private String userUpdateId;

}
