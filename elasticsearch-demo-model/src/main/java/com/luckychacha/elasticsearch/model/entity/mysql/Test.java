package com.luckychacha.elasticsearch.model.entity.mysql;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Test {
    private String id;

    private String message;

    private LocalDateTime postDate;

    private String user;
}