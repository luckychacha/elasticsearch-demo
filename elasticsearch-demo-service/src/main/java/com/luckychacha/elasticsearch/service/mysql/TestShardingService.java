package com.luckychacha.elasticsearch.service.mysql;

import com.luckychacha.elasticsearch.model.entity.mysql.TestSharding;
import com.luckychacha.elasticsearch.model.entity.mysql.TestShardingQueryVO;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface TestShardingService {
    List<TestSharding> getAll(TestShardingQueryVO testShardingQueryVO);

    List<TestSharding> getByRange(TestShardingQueryVO testShardingQueryVO);

    TestSharding getById(BigInteger id);

    int add(TestSharding testSharding);

    // 本地事务
    @ShardingTransactionType(TransactionType.LOCAL)
    // 两阶段事务（支持夸库事务）
//    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    int addTransaction(TestSharding testSharding, int succ);

    int edit(TestSharding testSharding);

    int delete(BigInteger id);
}
