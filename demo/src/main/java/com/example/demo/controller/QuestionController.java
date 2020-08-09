package com.example.demo.controller;

import com.example.demo.cache.HotTagCache;
import com.example.demo.dto.ResultDTO;
import com.example.demo.model.Comment;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.impl.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/5/6 7:33
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    NotificationServiceImpl notificationService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RecommendServiceImpl recommendService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        Question question = questionService.getAllByIdWithUser(id);
        List<Question> relationList = questionService.getAllByQuestionRelation(question);
        List<Comment> commentList = commentService.getAllById(id);
        questionService.incView(id);
        List<User> userList = userService.findAll();
        model.addAttribute("comments",commentList);
        model.addAttribute("question", question);
        model.addAttribute("relationQuestion",relationList);
        model.addAttribute("userList",userList);
        return "question";
    }

    @GetMapping("/query")
    public String queryquestion(@RequestParam(name = "tag") String tag,
                                @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                Model model){
        PageHelper.startPage(pageNum,5);
        List<Question> questionList = questionService.queryByTag(tag);
        PageInfo page = new PageInfo(questionList,5);
        model.addAttribute("questions",questionList);
        model.addAttribute("pageInfo",page);
        List<Question> titles = questionService.queryByView();
        model.addAttribute("hotTitles",titles);
        List<Question> tags = questionService.queryByTags();
        model.addAttribute("hotTags",tags);
        model.addAttribute("hotTags", HotTagCache.get());
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/question/like/{id}", method = RequestMethod.GET)
    public Object like(@PathVariable(name = "id") Long id,
                       @RequestParam(name = "userId") Long userId,
                       @RequestParam(name = "likeCount") Integer likeCount){
        notificationService.insert(id,userId,likeCount);
        recommendService.updateLike(id,userId,likeCount);
        questionService.updateLikeCount(id,likeCount);
        return ResultDTO.okOf();
    }

    @GetMapping("/delete_question")
    public String question(@Param("id") Long id){
        questionService.delete(id);
        return "redirect:profile/questionmanagement";
    }

    @ResponseBody
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public Object like(@RequestParam(name = "id") Long id){
        return questionService.getById(id);
    }

}
