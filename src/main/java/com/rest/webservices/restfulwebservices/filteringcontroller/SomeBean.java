package com.rest.webservices.restfulwebservices.filteringcontroller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"field1", "field2"})  // at class level also we can define the fields that needs to be ignored.
public class SomeBean {

    private String field1;

//    @JsonIgnore
    private String field2;
    private String field3;
}
