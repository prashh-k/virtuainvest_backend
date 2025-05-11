package com.example.virtuainvest_backend.virtuainvest_backend.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.virtuainvest_backend.virtuainvest_backend.Model.User;

public class UserPrinciple implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(() -> user.getRole());
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}