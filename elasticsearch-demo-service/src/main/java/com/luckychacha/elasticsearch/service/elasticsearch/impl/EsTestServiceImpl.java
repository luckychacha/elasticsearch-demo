package com.luckychacha.elasticsearch.service.elasticsearch.impl;

import com.luckychacha.elasticsearch.dao.elasticsearch.EsTestMapper;
import com.luckychacha.elasticsearch.model.entity.elasticsearch.EsTest;
import com.luckychacha.elasticsearch.service.elasticsearch.EsTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsTestServiceImpl implements EsTestService {

    private EsTestMapper esTestMapper;

    @Autowired
    public EsTestServiceImpl(EsTestMapper esTestMapper) {
        this.esTestMapper = esTestMapper;
    }

    @Override
    public List<EsTest> getAll() {
        return esTestMapper.selectTest();
    }
}
