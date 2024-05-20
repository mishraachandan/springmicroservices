package com.rest.webservices.restfulwebservices.controller;

import com.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.rest.webservices.restfulwebservices.entity.Post;
import com.rest.webservices.restfulwebservices.entity.User;
import com.rest.webservices.restfulwebservices.exception.UserNotfoundException;
import com.rest.webservices.restfulwebservices.repository.PostRepository;
import com.rest.webservices.restfulwebservices.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaController {
    private final UserDaoService userDaoService;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public UserJpaController(UserDaoService userDaoService, UserRepository userRepository, PostRepository postRepository) {
        this.userDaoService = userDaoService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // using constructor injection

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{userId}")
    public EntityModel<User> retrieveUser(@PathVariable int userId) throws UserNotfoundException {
//        User user = userDaoService.findOne(userId);
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new UserNotfoundException("No user was found for this id")));
        EntityModel<User> userEntityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        userEntityModel.add(link.withRel("all-users"));
        return userEntityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        // Here the location is returned in the headers.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                // because of the above http://localhost:8080/users is fetched..
                        buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
//        return ResponseEntity.created(null).build();
    }

    @GetMapping("/jpa/users")
    public ResponseEntity<List<?>> retrievePostsForUsers(@PathVariable int userId) throws UserNotfoundException {
//        userRepository.deleteById(userId);
//        if(message.toLowerCase().contentEquals("SuccessFully Deleted".toLowerCase())){
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        }
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotfoundException("No user was found for this id")));
//        EntityModel<User> userEntityModel = EntityModel.of(user.get());
//        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
//        userEntityModel.add(link.withRel("all-users"));

        List<?> posts = user.get().getPostList();
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    @PostMapping("/jpa/users/{userId}/post")
    public ResponseEntity<List<?>> createPostsForUsers(@PathVariable int userId, @Valid @RequestBody Post post) throws UserNotfoundException {
//        userRepository.deleteById(userId);
//        if(message.toLowerCase().contentEquals("SuccessFully Deleted".toLowerCase())){
//            return new ResponseEntity<>(message, HttpStatus.OK);


        Optional<User> user = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotfoundException("No user was found for this id")));
        post.setUser(user.get());
//        post.setDescription(post.getDescription());
        Post saved = postRepository.save(post);

//        EntityModel<User> userEntityModel = EntityModel.of(user.get());
//        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
//        userEntityModel.add(link.withRel("all-users"));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                // because of the above http://localhost:8080/users is fetched..
                        buildAndExpand(post.getId()).toUri();

        List<?> posts = user.get().getPostList();
        return ResponseEntity.created(location).build();

    }

}
