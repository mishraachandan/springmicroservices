package com.rest.webservices.restfulwebservices.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonFilter("Somebeanfilter")
public class Somebean {

    private String field1;
    private String field2;
    private String field3;


}
