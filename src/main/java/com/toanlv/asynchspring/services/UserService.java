package com.toanlv.asynchspring.services;

import com.toanlv.asynchspring.entities.User;
import com.toanlv.asynchspring.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Async
    public CompletableFuture<List<User>> saveUser(MultipartFile file) throws Exception{
       long start = System.currentTimeMillis();
       List<User> users = paserCsvFile(file);
       logger.info("saving List of user size: {}"+ users.size() + " {}"+Thread.currentThread().getName());
       users = repository.saveAll(users);
       long endtime = System.currentTimeMillis();
       logger.info("Total time :{} "+ (endtime - start));
       return CompletableFuture.completedFuture(users);
    }

    @Async
    public CompletableFuture<List<User>> getAllUser(){
        logger.info("getting all user {}", Thread.currentThread().getName());
        List<User> users = repository.findAll();
        return CompletableFuture.completedFuture(users);
    }

    private List<User> paserCsvFile(final MultipartFile file) throws Exception{
     final List<User> users = new ArrayList<>();
     try{
         try(final BufferedReader br = new BufferedReader(
                 new InputStreamReader(file.getInputStream()))){
             String line;
             while ((line=br.readLine())!=null){
                 final String[] dataline = line.split(",");
                 User user = new User();
                 user.setName(dataline[1]);
                 user.setEmail(dataline[2]);
                 user.setGender(dataline[3]);
                 users.add(user);

             }
         }
         return users;
     } catch (Exception e){
        logger.error(" Error convert file");
        throw new Exception("Fail to pase CSV file");
     }
    }
}
