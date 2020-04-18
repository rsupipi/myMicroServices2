package com.pipi.controller;

import com.pipi.bean.PostJPA;
import com.pipi.bean.UserJPA;
import com.pipi.repository.PostRepository;
import com.pipi.repository.UserRepository;

import com.pipi.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // <<<<<<<<<< User >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // get all ==================================================
    @GetMapping("jpa/users")
    public List<UserJPA> retrieveAllUsers() {
        return userRepository.findAll();
    }

    // get by ID ================================================
    @GetMapping("jpa/users/{id}")
    public Optional<UserJPA> retrieveUser(@PathVariable int id) {
        Optional<UserJPA> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }
        return user;
    }

    // save and update  =========================================
    @PostMapping("jpa/user")
    public UserJPA createUser(@RequestBody UserJPA user) {
        UserJPA userJPA = userRepository.save(user);
        return user;
    }

    // delete ==================================================
    @DeleteMapping("jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    //<<<<<<<<<< Post >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // get by ID ==================================================
    @GetMapping("jpa/users/{id}/posts")
    public List<PostJPA> retrieveUserPosts(@PathVariable int id) {
        Optional<UserJPA> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }
        return user.get().getPostJPAS();
    }

    // save and update  =========================================
    @PostMapping("jpa/users/{id}/post")
    public PostJPA createUserPost(@PathVariable int id, @RequestBody PostJPA post) {
        Optional<UserJPA> userJPAOptional = userRepository.findById(id);
        if (!userJPAOptional.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }

        UserJPA user = userJPAOptional.get();
        post.setUser(user);

        PostJPA postJPA = postRepository.save(post);
        return postJPA;
    }

    // save and update  =========================================
    @PostMapping("jpa/user/{id}/post")
    public PostJPA createPost(@PathVariable int id, @RequestBody PostJPA post) {
        Optional<UserJPA> userJPA = userRepository.findById(id);
        if (!userJPA.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }
        UserJPA user = userJPA.get();
        post.setUser(user);
        PostJPA postJPA = postRepository.save(post);
        return postJPA;
    }
}
