package com.luckychacha.elasticsearch.service.impl;

import com.luckychacha.elasticsearch.dao.TestMapper;
import com.luckychacha.elasticsearch.model.entity.Test;
import com.luckychacha.elasticsearch.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
