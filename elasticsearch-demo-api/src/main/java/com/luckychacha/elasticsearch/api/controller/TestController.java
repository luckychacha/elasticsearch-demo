package com.luckychacha.elasticsearch.api.controller;

import com.luckychacha.elasticsearch.dao.TestMapper;
import com.luckychacha.elasticsearch.model.entity.Test;
import com.luckychacha.elasticsearch.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class TestController {

    private TestService testService;


    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(value = "get-test-list")
    public void getTest() {
        Test test = new Test();
        List<Test> testList = testService.getAll();
        log.info("res:[{}]", testList);
    }
}
