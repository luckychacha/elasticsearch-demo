package com.luckychacha.elasticsearch.model.entity.mysql;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class TestSharding {
    private BigInteger id;

    private JsonContentDTO message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime postDate;

    private String user;
}