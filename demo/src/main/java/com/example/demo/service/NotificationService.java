package com.example.demo.service;

import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.NotificationDTO;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/5/16 11:44
 */
@Service
public interface NotificationService {

    List<Notification> query();

    int insert(Long id, Long userId, Integer likeCount);

    List<NotificationDTO> getByUserId(Long userId);

    int getNums(Long userId);

    void insertNotifier(CommentCreateDTO commentCreateDTO, User user);
}
