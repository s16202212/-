package com.example.demo.controller;

import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.impl.CommentServiceImpl;
import com.example.demo.service.impl.NotificationServiceImpl;
import com.example.demo.service.impl.QuestionServiceImpl;
import com.example.demo.service.impl.RecommendServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lai
 * @DateTime: 2020/5/8 0:14
 */
@Controller
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    NotificationServiceImpl notificationService;

    @Autowired
    RecommendServiceImpl recommendService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getUserId());
        comment.setLikeCount(0L);
        commentService.insert(comment, user);
        questionService.comcount(commentCreateDTO.getParentId());
        notificationService.insertNotifier(commentCreateDTO, user);
        recommendService.updateComment(commentCreateDTO.getParentId(), user.getUserId());
        return ResultDTO.okOf();
    }

    @GetMapping("delete_comment")
    public String deleteComment(@Param("id") Long id){
        commentService.delete(id);
        return "redirect:profile/commentmanagement";
    }
}
