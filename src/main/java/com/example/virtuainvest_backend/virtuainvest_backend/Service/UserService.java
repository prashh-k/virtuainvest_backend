package com.example.virtuainvest_backend.virtuainvest_backend.Service;

import com.example.virtuainvest_backend.virtuainvest_backend.Model.User;

public interface UserService {

    User register(User user) ;
    User getUserByUsername(String username);
    User getUserByUserId (Long userId);
}
