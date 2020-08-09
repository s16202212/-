package com.example.demo.model;

import lombok.Data;

/**
 * @Author: lai
 * @DateTime: 2020/4/27 10:58
 */
@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private String fileUrl;
    private String fileName;
    User user;
}
