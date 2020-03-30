package com.yocan.push.yuque.dto;

import lombok.Data;

@Data
public class RequestDto {
    private String sendkey;
    private String text;
    private String desp;
}
