package com.example.virtuainvest_backend.virtuainvest_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.virtuainvest_backend.virtuainvest_backend.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findByMobileNumber(String mobileNumber);
}
