package com.example.demo.dto;

import lombok.Data;

/**
 * @Author: lai
 * @DateTime: 2020/5/17 10:37
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long outerId;
    private String type;
    private String notifierName;
    private String outerTitle;
}
