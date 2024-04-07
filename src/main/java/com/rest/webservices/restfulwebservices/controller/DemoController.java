package com.rest.webservices.restfulwebservices.controller;

import com.rest.webservices.restfulwebservices.dto.DummyClass;
import com.rest.webservices.restfulwebservices.dto.HelloWorldBean;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class DemoController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "hello")
    public ResponseEntity<String> helloFunc(){
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }


    @PostMapping(path = "/helloBean")
    public ResponseEntity<String> helloWorldBean(@RequestBody @Valid HelloWorldBean helloWorldBean, BindingResult bindingResult){
//        StringBuilder errors = new StringBuilder("Validation errors: ");
        if (bindingResult.hasErrors()) {
            // If validation errors exist, return a ResponseEntity with the errors
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }
        return ResponseEntity.ok(new HelloWorldBean(helloWorldBean.getMessage()).toString());
//        return new HelloWorldBean(helloWorldBean.getMessage()).toString();
//        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    // building a simple @get method using path parameter

    @GetMapping(path = "hello/path-variable/{name}")
    public HelloWorldBean pathVaraibleCheck(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World to %s", name));
//        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }


    @PostMapping(path = "/test")
    public ResponseEntity<String> helloWorldBean(@Valid @RequestBody DummyClass dummyClass){
        return ResponseEntity.ok("String is valid");
    }

    @GetMapping(path = "/helloworldi18n")
    public ResponseEntity<String> helloworldi18n(){
        Locale locale = LocaleContextHolder.getLocale();
        return new ResponseEntity<>(messageSource.getMessage("good.morning.message", null, "Default message", locale), HttpStatus.OK);
    }
}
