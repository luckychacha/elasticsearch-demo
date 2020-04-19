package com.luckychacha.elasticsearch.dao.mysql;

import com.luckychacha.elasticsearch.model.entity.mysql.JsonContentDTO;
import com.luckychacha.elasticsearch.model.entity.mysql.Test;
import com.luckychacha.elasticsearch.model.entity.mysql.TestSharding;
import com.luckychacha.elasticsearch.model.entity.mysql.TestShardingNoid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Mapper
public interface TestShardingMapper {
    int insert(TestSharding record);

    int insertSelective(TestSharding record);

    List<TestSharding> selectTestSharding();

    List<TestSharding> selectTestShardingByRange(@Param("startDate" ) LocalDateTime startDate,
                                                 @Param("endDate" ) LocalDateTime endDate,
                                                 @Param("start" ) Long start,
                                                 @Param("length" ) Long length,
                                                 @Param("user") String user);

    TestSharding selectByPrimaryKey(@Param("id" ) BigInteger id);

    int updateByPrimaryKeySelective(@Param("message" ) JsonContentDTO jsonContentDTO, @Param("id" ) BigInteger id);

    int deleteByPrimaryKey(@Param("id") BigInteger id);
}