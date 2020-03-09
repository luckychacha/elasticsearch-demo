package com.luckychacha.elasticsearch.model.entity.elasticsearch;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EsTest {
    private String id;

    private String message;

    private LocalDateTime postDate;

    private String user;
}