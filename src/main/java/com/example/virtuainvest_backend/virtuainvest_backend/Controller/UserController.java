package com.example.virtuainvest_backend.virtuainvest_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.virtuainvest_backend.virtuainvest_backend.Entity.User;
import com.example.virtuainvest_backend.virtuainvest_backend.Service.UserServiceImpl;



@RestController
@RequestMapping("/api")
public class UserController {


    UserServiceImpl userService ;

    @Autowired
    UserController (UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register") 
    public ResponseEntity<User> registerUser (@RequestBody User user) {
        User newUser = userService.register(user);
        if(newUser != null) {
            return ResponseEntity.status(200).body(newUser);
        }
        return ResponseEntity.status(500).build();
    }


    @GetMapping("/user/username/{username}")
    public ResponseEntity<User> getUserName(@PathVariable String username) {
        User newUser = userService.getUserByUsername(username);
        if(newUser != null) {
            return ResponseEntity.status(200).body(newUser);
        }
        return ResponseEntity.status(500).build();
    }
    

    @GetMapping("/user/id/{userId}")
    public ResponseEntity<User> getUserId(@PathVariable Long userId) {
        User newUser = userService.getUserByUserId(userId);
        if(newUser != null) {
            return ResponseEntity.status(200).body(newUser);
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userList = userService.getAllUser();
        if(userList.isEmpty()) {
            return ResponseEntity.status(500).build();
        }
        else {
            return ResponseEntity.status(200).body(userList);

        }
    }
    
}
