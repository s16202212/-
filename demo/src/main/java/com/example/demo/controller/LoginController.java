package com.example.demo.controller;

import com.example.demo.cache.HotTagCache;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.impl.NotificationServiceImpl;
import com.example.demo.service.impl.QuestionServiceImpl;
import com.example.demo.service.impl.RecommendServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/27 10:48
 */
@Controller
public class LoginController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    NotificationServiceImpl notificationService;

    @Autowired
    RecommendServiceImpl recommendService;

    @GetMapping("/login")
    public String Login(){
        return "index";
    }

    @PostMapping("/login")
    public String Login(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                        @Param("email") String email,
                        @Param("password") String password,
                        HttpSession session,
                        Model model) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            int notifiernum = notificationService.getNums(user.getUserId());
            session.setAttribute("notifiernum",notifiernum);
            PageHelper.startPage(pageNum,5);
            List<Question> questionList = questionService.recommend(user.getUserId());
            model.addAttribute("questions",questionList);
            List<User> userList = userService.findAll();
            model.addAttribute("userList",userList);
            PageInfo page = new PageInfo(questionList,5);
            model.addAttribute("pageInfo",page);
            List<Question> titles = questionService.queryByView();
            model.addAttribute("hotTitles",titles);
            model.addAttribute("hotTags", HotTagCache.get());
            System.out.println("login");
            System.out.println(page);
            return "login";
        } else {
            return "redirect:/";
        }

    }

    @PostMapping("/regist")
    public String regist(@Param("userId") Long userId,
                         @Param("email") String email,
                         @Param("name") String name,
                         @Param("password") String password,
                         @Param("avatarUrl") String avatarUrl){
        User user = new User();
        user.setUserId(userId);
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setAvatarUrl(avatarUrl);
        userService.createOrUpdate(user);
        return "redirect:/";

    }

    @GetMapping("/loginout")
    public String Loginout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/delete_user")
    public String question(@Param("userId") Long userId){
        userService.delect(userId);
        return "redirect:profile/usermanagement";
    }

}
