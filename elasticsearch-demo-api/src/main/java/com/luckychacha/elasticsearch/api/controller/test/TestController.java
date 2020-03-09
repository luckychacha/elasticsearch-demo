package com.luckychacha.elasticsearch.api.controller.test;

import com.luckychacha.elasticsearch.model.entity.elasticsearch.EsTest;
import com.luckychacha.elasticsearch.service.elasticsearch.EsTestService;
import com.luckychacha.elasticsearch.service.mysql.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class TestController {

    private EsTestService estestService;


    @Autowired
    public TestController(EsTestService esTestService) {
        this.estestService = estestService;
    }

    @GetMapping(value = "get-test-list")
    public void getTest() {
        EsTest test = new EsTest();
        List<EsTest> testList = estestService.getAll();
        log.info("res:[{}]", testList);
    }
}
