package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lai
 * @DateTime: 2020/5/16 11:49
 */
@Controller
public class NotificationController {

    @Autowired
    NotificationServiceImpl notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return "redirect:/";
    }
}
