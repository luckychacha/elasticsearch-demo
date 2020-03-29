package com.luckychacha.elasticsearch.service.mysql.impl;

import com.luckychacha.elasticsearch.dao.mysql.TestShardingMapper;
import com.luckychacha.elasticsearch.model.entity.mysql.Test;
import com.luckychacha.elasticsearch.model.entity.mysql.TestSharding;
import com.luckychacha.elasticsearch.model.entity.mysql.TestShardingNoid;
import com.luckychacha.elasticsearch.service.mysql.TestShardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TestShardingServiceImpl implements TestShardingService {

    private TestShardingMapper testShardingMapper;

    @Autowired
    public TestShardingServiceImpl(TestShardingMapper testShardingMapper) {
        this.testShardingMapper = testShardingMapper;
    }

    @Override
    public List<TestSharding> getAll() {
        return testShardingMapper.selectTestSharding();
    }

    @Override
    public List<TestSharding> getByRange(LocalDateTime startDate, LocalDateTime endDate) {
        return testShardingMapper.selectTestShardingByRange(startDate, endDate);
    }

    @Override
    public TestSharding getById(BigInteger id) {
        return testShardingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int add(TestSharding testSharding) {
        return testShardingMapper.insertSelective(testSharding);
    }


    @Override
    public int edit(TestSharding testSharding) {
        BigInteger id = testSharding.getId();
        return testShardingMapper.updateByPrimaryKeySelective(testSharding.getMessage(), id);
    }

    @Override
    public int delete(BigInteger id) {
        return testShardingMapper.deleteByPrimaryKey(id);
    }
}
