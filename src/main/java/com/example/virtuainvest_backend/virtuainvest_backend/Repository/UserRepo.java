package com.example.virtuainvest_backend.virtuainvest_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.virtuainvest_backend.virtuainvest_backend.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findByMobileNumber(String mobileNumber);

    // Check if a user with a given username exists
    boolean existsByUsername(String username);

    // Check if a user with a given email exists
    boolean existsByEmail(String email);

}
