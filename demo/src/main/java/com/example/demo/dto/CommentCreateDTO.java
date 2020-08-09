package com.example.demo.dto;

import lombok.Data;

/**
 * @Author: lai
 * @DateTime: 2020/5/8 0:48
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}