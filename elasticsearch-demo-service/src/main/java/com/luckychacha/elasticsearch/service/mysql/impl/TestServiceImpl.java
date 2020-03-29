package com.luckychacha.elasticsearch.service.mysql.impl;

import com.luckychacha.elasticsearch.common.util.JsonUtil;
import com.luckychacha.elasticsearch.dao.mysql.TestMapper;
import com.luckychacha.elasticsearch.model.entity.mysql.JsonContentDTO;
import com.luckychacha.elasticsearch.model.entity.mysql.SubJsonContentDTO;
import com.luckychacha.elasticsearch.model.entity.mysql.Test;
import com.luckychacha.elasticsearch.model.entity.mysql.TestDTO;
import com.luckychacha.elasticsearch.service.mysql.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    private TestMapper testMapper;

    @Autowired
    public TestServiceImpl(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Override
    public List<Test> getAll() {
        return testMapper.selectTest();
    }

    @Override
    public String getOne() {
//        Test a = new Test();
//        JsonContentDTO jsonContentDTO = new JsonContentDTO();
//        SubJsonContentDTO subJsonContentDTO = new SubJsonContentDTO();
//        jsonContentDTO.setQuz(subJsonContentDTO);
//        a.setMessage(jsonContentDTO);
//        String res = JsonUtil.toJson(a);
//
//        Test test = JsonUtil.fromJson(res, Test.class);
//
//        return JsonUtil.toJson(test);
        return null;
    }

    @Override
    public int add(Test test) {
        return testMapper.insertSelective(test);
    }
}
