package com.toanlv.asynchspring.controller;

import com.toanlv.asynchspring.entities.User;
import com.toanlv.asynchspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value = "/users",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam MultipartFile[] files) throws Exception {
        for (MultipartFile file:files){
            service.saveUser(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> getUsers(){
     return  service.getAllUser().thenApply(ResponseEntity::ok);
    }
}
