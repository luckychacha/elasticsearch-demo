package com.luckychacha.elasticsearch.service.mysql;

import com.luckychacha.elasticsearch.model.entity.mysql.Test;

import java.util.List;
public interface TestService {
    List<Test> getAll();
}
