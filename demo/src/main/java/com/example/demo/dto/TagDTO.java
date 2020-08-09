package com.example.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/5/16 9:44
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}