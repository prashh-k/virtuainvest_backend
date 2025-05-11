package com.example.virtuainvest_backend.virtuainvest_backend.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.virtuainvest_backend.virtuainvest_backend.Model.User;
import com.example.virtuainvest_backend.virtuainvest_backend.Repository.UserRepo;
import com.example.virtuainvest_backend.virtuainvest_backend.config.JwtService;
import com.example.virtuainvest_backend.virtuainvest_backend.config.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepo userRepo, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(409).body("Email already exists");
        }

        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user) {
    System.out.println("Trying to authenticate: " + user.getUsername());

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
    );

    if (authentication.isAuthenticated()) {
        String token = jwtService.generateToken(user.getUsername());
        System.out.println("Login successful! Generated Token: " + token);
        return ResponseEntity.ok(token);
    } else {
        System.out.println("Authentication failed.");
        return ResponseEntity.status(403).body("Invalid credentials");
    }
}

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(404).build();
    }
}