package com.luckychacha.elasticsearch.dao.elasticsearch;

import com.luckychacha.elasticsearch.model.entity.elasticsearch.EsTest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface EsTestMapper {
    int insert(EsTest record);

    int insertSelective(EsTest record);

    List<EsTest> selectTest();
}