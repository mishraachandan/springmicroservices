package com.rest.webservices.restfulwebservices.filteringcontroller;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonFilter("SomeBeanFilter")
public class SomeBeanNew {

    private String field1;

    private String field2;

    //@JsonIgnore
    private String field3;


}
