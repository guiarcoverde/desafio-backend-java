package com.picpaydesafio.services;

import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.domain.user.UserType;
import com.picpaydesafio.dtos.UserDTO;
import com.picpaydesafio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.STORE) {
            throw new Exception("User type STORE is not authorized to make this transaction");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public User createUser(UserDTO data) {
        User newUser =  new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
