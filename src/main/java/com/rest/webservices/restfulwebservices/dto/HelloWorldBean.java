package com.rest.webservices.restfulwebservices.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class HelloWorldBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 4, max = 12, message = "The size of message exceeds the range.")
    private String message;

    private String name;

    public HelloWorldBean(){}

    public HelloWorldBean(String message) {
        this.message = message;
    }

//    public String getMessage() {
//        return message;
//    }
//    public String getMessage(){
//        return message;

    public void setMessage(String message) {
        this.message = message;
    }
//    }

//    public void setMessage(String message){
//        this.message = message;
//    }

    @Override
    public String toString(){
        return "HelloWorldBean [message=" + message +"]";
    }
}
