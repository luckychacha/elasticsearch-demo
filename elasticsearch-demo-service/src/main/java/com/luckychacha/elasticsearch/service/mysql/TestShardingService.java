package com.luckychacha.elasticsearch.service.mysql;

import com.luckychacha.elasticsearch.model.entity.mysql.TestSharding;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface TestShardingService {
    List<TestSharding> getAll();

    List<TestSharding> getByRange(LocalDateTime startDate, LocalDateTime endDate);

    TestSharding getById(BigInteger id);

    int add(TestSharding testSharding);

    int edit(TestSharding testSharding);

    int delete(BigInteger id);
}
