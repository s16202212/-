package com.example.demo.service.impl;

import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.NotificationDTO;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Notification;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/5/16 11:45
 */
@Service
public class NotificationServiceImpl implements com.example.demo.service.NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Notification> query() {
        return notificationMapper.queryAll();
    }

    @Override
    public int insert(Long id, Long userId, Integer likeCount) {
        Question question = questionMapper.getById(id);
        User user = userMapper.findById(userId);
        Notification notification = new Notification();
        int i = likeCount;
        int j = question.getLikeCount();
        if (i == j + 1) {
            notification.setNotifier(userId);
            notification.setReceiver(question.getCreator());
            notification.setOuterid(question.getId());
            notification.setType(1);
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setStatus(1);
            notification.setNotifierName(user.getName());
            notification.setOuterTitle(question.getTitle());
            return notificationMapper.insert(notification);
        }
        if (i == j - 1){
            notification.setType(1);
            return notificationMapper.delectByThree(userId,question.getCreator(),notification.getType());
        }
        return 0;
    }

    @Override
    public List<NotificationDTO> getByUserId(Long userId) {
        List<Notification> notificationList = notificationMapper.getByUserId(userId);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        Iterator<Notification> it = notificationList.iterator();
        while (it.hasNext()){
            Notification notification = it.next();
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setId(notification.getId());
            notificationDTO.setOuterId(notification.getOuterid());
            notificationDTO.setNotifierName(notification.getNotifierName());
            notificationDTO.setOuterTitle(notification.getOuterTitle());
            if (notification.getType() == 1){
                notificationDTO.setType("点赞了你的问题");
            }if (notification.getType() == 2){
                notificationDTO.setType("评论了你的问题");
            }
            notificationDTOS.add(notificationDTO);
        }
        return notificationDTOS;
    }

    @Override
    public int getNums(Long userId) {
        return notificationMapper.getNums(userId);
    }

    @Override
    public void insertNotifier(CommentCreateDTO commentCreateDTO, User user) {
        Question question = questionMapper.getById(commentCreateDTO.getParentId());
        Notification notification = new Notification();
        notification.setNotifier(user.getUserId());
        notification.setReceiver(question.getCreator());
        notification.setOuterid(commentCreateDTO.getParentId());
        notification.setType(2);
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setStatus(1);
        notification.setNotifierName(user.getName());
        notification.setOuterTitle(question.getTitle());
        notificationMapper.insert(notification);
    }

}
