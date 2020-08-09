package com.example.demo.model;

import lombok.Data;

/**
 * @Author: lai
 * @DateTime: 2020/5/8 0:16
 */
@Data
public class Comment {

    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;
    User user;
}