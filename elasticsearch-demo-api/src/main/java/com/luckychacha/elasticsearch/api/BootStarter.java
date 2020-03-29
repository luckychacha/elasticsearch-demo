package com.luckychacha.elasticsearch.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@ComponentScan("com.luckychacha.elasticsearch")
@MapperScan(basePackages={"com.luckychacha.elasticsearch.dao.elasticsearch","com.luckychacha.elasticsearch.dao.mysql"})
public class BootStarter {
    public static void main(String[] args) {
        SpringApplication.run(BootStarter.class, args);
    }

}

