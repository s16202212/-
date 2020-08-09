package com.example.demo.controller;

import com.example.demo.cache.HotTagCache;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.impl.CommentServiceImpl;
import com.example.demo.service.impl.QuestionServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/27 10:28
 */
@Controller
public class IndexController {

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                        Model model) {
        PageHelper.startPage(pageNum,5);
        List<Question> questionList = questionService.queryAll();
        model.addAttribute("questions",questionList);
        List<User> userList = userService.findAll();
        model.addAttribute("userList",userList);
        PageInfo page = new PageInfo(questionList,5);
        model.addAttribute("pageInfo",page);
        List<Question> titles = questionService.queryByView();
        model.addAttribute("hotTitles",titles);
        model.addAttribute("hotTags", HotTagCache.get());
        System.out.println("index");
        System.out.println(page);
        return "index";
    }

}
