package com.ankush.karantraders.data.service;

import java.util.List;

import com.ankush.karantraders.data.entities.User;
import com.ankush.karantraders.data.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User getByUsername(String username) {
        return repository.findByUsername(username);
    }
    public List<String>getAllUserNames()
    {
        return repository.getAllUsernames();
    }
    public int saveUser(User user)
    {
        repository.save(user);
        return 1;
    }
    public List<User>getAllUser()
    {
        return repository.findAll();
    }
}

