package com.example.demo.dto;

import lombok.Data;

/**
 * @Author: lai
 * @DateTime: 2020/6/3 21:22
 */
@Data
public class CommentQueryAllDTO {
    private Long id;
    private String commentator;
    private String publisher;
    private String content;
}
