package com.example.demo.model;

import lombok.Data;

/**
 * @Author: lai
 * @DateTime: 2020/4/27 10:46
 */
@Data
public class User {
    private Long userId;
    private String email;
    private String name;
    private String password;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private String fileName;
    private String fileUrl;
}