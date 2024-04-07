package com.rest.webservices.restfulwebservices.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Validated
public class DummyClass extends ParentClass {
    @Size(min = 0, max = 10)
    private String msg;

//    @Valid
    @Size(min = 0, max = 1)
    private String flag;
}
