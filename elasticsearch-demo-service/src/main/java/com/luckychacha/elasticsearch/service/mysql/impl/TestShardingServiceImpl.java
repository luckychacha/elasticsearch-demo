package com.luckychacha.elasticsearch.service.mysql.impl;

import com.luckychacha.elasticsearch.dao.mysql.TestShardingMapper;
import com.luckychacha.elasticsearch.model.entity.mysql.Test;
import com.luckychacha.elasticsearch.model.entity.mysql.TestSharding;
import com.luckychacha.elasticsearch.model.entity.mysql.TestShardingNoid;
import com.luckychacha.elasticsearch.model.entity.mysql.TestShardingQueryVO;
import com.luckychacha.elasticsearch.service.mysql.TestShardingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
    public List<TestSharding> getAll(TestShardingQueryVO testShardingQueryVO) {
        return testShardingMapper.selectTestSharding();
    }

    @Override
    public List<TestSharding> getByRange(TestShardingQueryVO testShardingQueryVO) {
        return testShardingMapper.selectTestShardingByRange(
                testShardingQueryVO.getStartDate(),
                testShardingQueryVO.getEndDate(),
                testShardingQueryVO.getStart(),
                testShardingQueryVO.getLength(),
                testShardingQueryVO.getUser()
        );
    }

    @Override
    public TestSharding getById(BigInteger id) {
        return testShardingMapper.selectByPrimaryKey(id);
    }

    // 本地事务
    @ShardingTransactionType(TransactionType.LOCAL)
    // 两阶段事务（支持夸库事务）
//    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    @Override
    public int add(TestSharding testSharding) {
        try{
            int res = testShardingMapper.insertSelective(testSharding);
            log.error("新增成功");

            return res;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info("新增失败：",e);
            return 0;
        }
    }
    // 本地事务
    @ShardingTransactionType(TransactionType.LOCAL)
    // 两阶段事务（支持夸库事务）
//    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    @Override
    public int addTransaction(TestSharding testSharding, int succ) {
        try{
            Integer res = testShardingMapper.insertSelective(testSharding);
            if (succ == 0) {
                throw new Exception("aaa");
            }
            return 1;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("新增失败：",e);
            return 0;
        }
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
