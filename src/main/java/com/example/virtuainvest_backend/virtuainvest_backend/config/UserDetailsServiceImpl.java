package com.example.virtuainvest_backend.virtuainvest_backend.config;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import com.example.virtuainvest_backend.virtuainvest_backend.Model.User;
import com.example.virtuainvest_backend.virtuainvest_backend.Repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // Assigns role dynamically
                .build();
    }
}