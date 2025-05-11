package com.example.virtuainvest_backend.virtuainvest_backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.example.virtuainvest_backend.virtuainvest_backend.DTO.LoginRequestDTO;
import com.example.virtuainvest_backend.virtuainvest_backend.DTO.UserDTO;
import com.example.virtuainvest_backend.virtuainvest_backend.Exception.UserAlreadyExistsException;
import com.example.virtuainvest_backend.virtuainvest_backend.Model.User;
import com.example.virtuainvest_backend.virtuainvest_backend.Repository.UserRepo;
import com.example.virtuainvest_backend.virtuainvest_backend.Service.UserService;
import com.example.virtuainvest_backend.virtuainvest_backend.Service.UserServiceImpl;
import com.example.virtuainvest_backend.virtuainvest_backend.config.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private UserServiceImpl userService;  // or use UserService interface


    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepo userRepo,
            UserServiceImpl userService,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        // Encode the password here

        // Create and save user — avoid double encoding in constructor
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword()); // already encoded
        newUser.setEmail(userDTO.getEmail());
        newUser.setMobileNumber(userDTO.getMobileNumber());
        newUser.setRole(userDTO.getRole());

        try {
            newUser = userService.register(newUser);
            return ResponseEntity.status(201).body(newUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).body("User already exists: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            if (auth.isAuthenticated()) {
                String token = jwtService.generateToken(loginDTO.getUsername());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
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
