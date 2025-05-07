package com.example.virtuainvest_backend.virtuainvest_backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.virtuainvest_backend.virtuainvest_backend.Entity.User;
import com.example.virtuainvest_backend.virtuainvest_backend.Repository.UserRepo;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    UserRepo userRepo ;

    @Autowired
    UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User register(User user) {
        return userRepo.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User getUserByUserId(Long userId) {
        Optional<User> optUser = userRepo.findById(userId);
        if(optUser.isPresent()){
            return optUser.get();
        }else {
            return null;
        }
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    


}
