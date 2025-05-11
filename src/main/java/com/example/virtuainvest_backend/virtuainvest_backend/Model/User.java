package com.example.virtuainvest_backend.virtuainvest_backend.Model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String username;
    private String mobileNumber;
    private String role; // "USER", "ADMIN"

    @Transient
    private String token; // Optional field for storing JWT token temporarily

    public User() {}

    public User(String email, String password, String username, String mobileNumber, String role) {
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password); // Hash password before saving
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.role = role;
    }

    // Getters & Setters

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = new BCryptPasswordEncoder().encode(password); }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", email=" + email + ", username=" + username +
                ", mobileNumber=" + mobileNumber + ", role=" + role + ", token=" + token + "]";
    }
}