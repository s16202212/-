package com.example.demo.controller;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.model.Comment;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.CommentServiceImpl;
import com.example.demo.service.impl.NotificationServiceImpl;
import com.example.demo.service.impl.QuestionServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/22 11:11
 */
@Controller
public class ProfileController {

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    NotificationServiceImpl notificationService;

    @Autowired
    UserService userService;

    @Autowired
    CommentServiceImpl commentService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                          Model model){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageHelper.startPage(pageNum,5);
            List<Question> questionList = questionService.queryById(user.getUserId());
            PageInfo page = new PageInfo(questionList,5);
            model.addAttribute("questions",questionList);
            model.addAttribute("pageInfo",page);
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            List<NotificationDTO> notificationList = notificationService.getByUserId(user.getUserId());
            model.addAttribute("notification",notificationList);
        }else if ("information".equals(action)){
            model.addAttribute("section","information");
            model.addAttribute("sectionName","个人信息");
            User user1 = userService.findById(user.getUserId());
            model.addAttribute("user",user1);
        }else if ("usermanagement".equals(action)) {
            model.addAttribute("section", "usermanagement");
            model.addAttribute("sectionName", "用户信息后台管理");
            PageHelper.startPage(pageNum,5);
            List<User> userList = userService.findAll();
            PageInfo page = new PageInfo(userList,5);
            model.addAttribute("users",userList);
            model.addAttribute("pageInfo",page);
        }else if ("questionmanagement".equals(action)) {
            model.addAttribute("section", "questionmanagement");
            model.addAttribute("sectionName", "问题信息后台管理");
            PageHelper.startPage(pageNum,5);
            List<Question> questionList = questionService.queryAll();
            PageInfo page = new PageInfo(questionList,5);
            model.addAttribute("questions",questionList);
            model.addAttribute("pageInfo",page);
        }else if ("commentmanagement".equals(action)) {
            model.addAttribute("section", "commentmanagement");
            model.addAttribute("sectionName", "评论信息后台管理");
            PageHelper.startPage(pageNum,5);
            List<Comment> commentList = commentService.queryAll();
            PageInfo page = new PageInfo(commentList,5);
            model.addAttribute("comments",commentList);
            model.addAttribute("pageInfo",page);
        }
        return "profile";
    }

}
