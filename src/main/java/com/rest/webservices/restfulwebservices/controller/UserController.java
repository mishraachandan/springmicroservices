package com.rest.webservices.restfulwebservices.controller;

import com.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.rest.webservices.restfulwebservices.entity.User;
import com.rest.webservices.restfulwebservices.exception.UserNotfoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private final UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService){
        this.userDaoService = userDaoService;
    }

    // using constructor injection

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<User> retrieveUser(@PathVariable int userId) throws UserNotfoundException {
        User user = userDaoService.findOne(userId);
        if(user == null){
            throw new UserNotfoundException("No user was found for this id");
        }
        EntityModel<User> userEntityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        userEntityModel.add(link.withRel("all-users"));
        return userEntityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userDaoService.save(user);

        // Here the location is returned in the headers.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
        // because of the above http://localhost:8080/users is fetched..
                buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
//        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) throws UserNotfoundException {
        String message = userDaoService.deleteById(userId);
        if(message.toLowerCase().contentEquals("SuccessFully Deleted".toLowerCase())){
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

    }


}
