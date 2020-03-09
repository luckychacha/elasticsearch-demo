package com.luckychacha.elasticsearch.service.elasticsearch;
import com.luckychacha.elasticsearch.model.entity.elasticsearch.EsTest;

import java.util.List;

public interface EsTestService {
    List<EsTest> getAll();
}
