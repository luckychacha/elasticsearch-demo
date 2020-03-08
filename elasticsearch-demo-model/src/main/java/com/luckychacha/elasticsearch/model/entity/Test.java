package com.luckychacha.elasticsearch.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Test {
    private String id;

    private String message;

    private LocalDateTime postdate;

    private String user;
}