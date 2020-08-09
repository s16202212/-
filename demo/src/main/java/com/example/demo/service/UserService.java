package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/27 10:46
 */

public interface UserService {

    User login(String email, String password);

    User findById(Long userId);

    void createOrUpdate(User user);

    List<User> findAll();

    void delect(Long userId);
}
