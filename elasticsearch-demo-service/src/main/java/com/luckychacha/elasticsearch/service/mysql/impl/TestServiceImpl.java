package com.luckychacha.elasticsearch.service.mysql.impl;

import com.luckychacha.elasticsearch.dao.mysql.TestMapper;
import com.luckychacha.elasticsearch.model.entity.mysql.Test;
import com.luckychacha.elasticsearch.service.mysql.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
