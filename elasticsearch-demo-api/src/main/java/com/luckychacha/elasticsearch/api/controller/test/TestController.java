package com.luckychacha.elasticsearch.api.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckychacha.elasticsearch.common.util.JsonUtil;
import com.luckychacha.elasticsearch.model.entity.mysql.Test;
import com.luckychacha.elasticsearch.model.entity.mysql.TestSharding;
import com.luckychacha.elasticsearch.model.entity.mysql.TestShardingQueryVO;
import com.luckychacha.elasticsearch.service.mysql.TestService;
import com.luckychacha.elasticsearch.service.mysql.TestShardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

    private TestService testService;
    private TestShardingService testShardingService;


    @Autowired
    public TestController(TestService testService,
                          TestShardingService testShardingService) {
        this.testService =testService;
        this.testShardingService = testShardingService;
    }

    @PostMapping(value = "add-test")
    public void addTest(@RequestBody Test test) {
        this.testService.add(test);
    }

    @GetMapping(value = "get-test-list")
    public String getList() {

        return JsonUtil.toJson(testService.getAll());
    }


    @PostMapping(value = "get-test-sharding-list")
    public String getAllTestSharding(@RequestBody TestShardingQueryVO testShardingQueryVO) {
        return JsonUtil.toJson(this.testShardingService.getAll(testShardingQueryVO));
    }


    @PostMapping(value = "add-test-sharding")
    public void addTestSharding(@RequestBody TestSharding testSharding) {
        this.testShardingService.add(testSharding);
    }


    @PostMapping(value = "add-test-sharding-transaction/{succ}")
    public int addTestShardingTransaction(@RequestBody TestSharding testSharding, @PathVariable("succ") int succ) {
        return testShardingService.addTransaction(testSharding, succ);
    }


    @PostMapping(value = "edit-test-sharding")
    public void editTestSharding(@RequestBody TestSharding testSharding) {
        this.testShardingService.edit(testSharding);
    }

    @PostMapping(value = "get-test-sharding-range")
    public List<TestSharding> getTestShardingRange(@RequestBody TestShardingQueryVO testShardingQueryVO) {

        return testShardingService.getByRange(testShardingQueryVO);
    }

    @GetMapping(value = "get-test-sharding-by-id/{id}")
    public TestSharding getTestSharding(@PathVariable("id") BigInteger id) {
        return testShardingService.getById(id);
    }


    @DeleteMapping(value = "delete-test-sharding-by-id/{id}")
    public void deleteTestSharding(@PathVariable("id") BigInteger id) {
        testShardingService.delete(id);
    }

    @GetMapping(value = "complex-test")
    public String getComplex () {
        return testService.getOne();
    }
}
