package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/5/8 0:28
 */
@Service
public interface CommentService {

    void insert(Comment comment, User user);

    List<Comment> queryAll();

    Comment getAllByIdWithUser(Long id);

    List<Comment> getAllById(Long id);

    void delete(Long id);

    //    List<CommentQueryAllDTO> queryByUserAndQuestion();
}
