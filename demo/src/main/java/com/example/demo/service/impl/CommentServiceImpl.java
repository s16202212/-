package com.example.demo.service.impl;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/5/8 0:29
 */
@Service
public class CommentServiceImpl implements com.example.demo.service.CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public void insert(Comment comment, User user) {
        commentMapper.insert(comment);
    }

    @Override
    public List<Comment> queryAll() {
        return commentMapper.queryAll();
    }

    @Override
    public Comment getAllByIdWithUser(Long id) {
        return commentMapper.getAllByIdWithUser(id);
    }

    @Override
    public List<Comment> getAllById(Long id) {
        return commentMapper.getAllById(id);
    }

    @Override
    public void delete(Long id) {
        commentMapper.delete(id);
    }

    //    @Override
//    public List<CommentQueryAllDTO> queryByUserAndQuestion() {
//        List<CommentQueryAllDTO> commentQueryAllDTOS = new ArrayList<>();
//        List<Comment> commentList = commentMapper.queryAll();
//        Iterator<Comment> it = commentList.iterator();
//        while(it.hasNext()){
//            CommentQueryAllDTO commentQueryAllDTO = new CommentQueryAllDTO();
//            commentQueryAllDTO.setId();
//        }
//        return commentQueryAllDTOS;
//    }

}
