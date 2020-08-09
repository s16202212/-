package com.example.demo.service.impl;

import com.example.demo.mapper.RecommendMapper;
import com.example.demo.model.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/27 10:46
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RecommendMapper recommendMapper;

    @Override
    public User login(String email, String password) {
        return userMapper.login(email,password);
    }

    @Override
    public User findById(Long userId) {
        return userMapper.findById(userId);
    }

    @Override
    public void createOrUpdate(User user) {
        if (user.getUserId() == null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.regist(user);
            User user1 = userMapper.findNear();
            Recommend recommend = new Recommend();
            recommend.setUid(user1.getUserId());
            recommend.setJava(0);
            recommend.setSpring(0);
            recommend.setPython(0);
            recommend.setPhp(0);
            recommend.setCss(0);
            recommendMapper.insert(recommend);
        }else {
            user.setGmtModified(user.getGmtCreate());
            userMapper.update(user);
        }
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public void delect(Long userId) {
        userMapper.delect(userId);
    }
}
