package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByAccountuserid(String accountuserid) {
        return userRepository.findByAccountuserid(accountuserid);
    }


    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String accountuserid, User user) {
        Optional<User> existingUserOpt = userRepository.findByAccountuserid(accountuserid);

        if (existingUserOpt.isEmpty()) {
            return null; 
        }
        User existingUser = existingUserOpt.get();
        existingUser.setFullname(user.getFullname());
        existingUser.setPhone(user.getPhone());
        existingUser.setAddress(user.getAddress());
        existingUser.setDepartment(user.getDepartment());
        existingUser.setOld(user.getOld());

        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    public boolean existsByAccountUserId(String accountuserid) {
        return userRepository.existsByAccountuserid(accountuserid);
    }
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }
}
