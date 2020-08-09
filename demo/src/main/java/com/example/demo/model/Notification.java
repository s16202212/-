package com.example.demo.model;

import lombok.Data;

/**
 * @Author: lai
 * @DateTime: 2020/5/16 11:35
 */
@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerid;
    private int type;
    private Long gmtCreate;
    private int status;
    private String notifierName;
    private String outerTitle;
}
