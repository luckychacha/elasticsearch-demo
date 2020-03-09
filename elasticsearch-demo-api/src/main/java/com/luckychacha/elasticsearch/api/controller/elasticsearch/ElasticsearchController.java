package com.luckychacha.elasticsearch.api.controller.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckychacha.elasticsearch.model.entity.elasticsearch.EsTest;
import com.luckychacha.elasticsearch.model.entity.mysql.Test;
import com.luckychacha.elasticsearch.service.elasticsearch.EsTestService;
import com.luckychacha.elasticsearch.service.mysql.TestService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

@Slf4j
@RestController
public class ElasticsearchController {
    public static final String INDEX_NAME = "test";
    public static final String TYPE_NAME = "test_type";

    private RestHighLevelClient restHighLevelClient;
    private EsTestService esTestService;

    @Autowired
    public ElasticsearchController(EsTestService esTestService,
                                   TestService testService,
                                   RestHighLevelClient restHighLevelClient) {
        this.esTestService = esTestService;
        this.testService = testService;
        this.restHighLevelClient = restHighLevelClient;
    }

    //    @GetMapping(value = "add-document")
    @PostMapping(value = "add-document")
    public void addIndex(@RequestBody EsTest testBean) {

//        Test testBean = new Test();
//        testBean.setId("1");
//        testBean.setUser("Kimuchi");
//        testBean.setPostDate(LocalDateTime.now());
//        testBean.setMessage("trying out of elastic");
        log.info("[{}]", testBean);
        try {
            Map<String, String> test = BeanUtils.describe((Object) testBean);

            IndexRequest request =  new IndexRequest(INDEX_NAME, TYPE_NAME).id(testBean.getId()).source(test);

            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("index[{}]创建成功: [{}]。", INDEX_NAME, new ObjectMapper().writeValueAsString(response));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            log.error("入参错误。");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("index[{}]创建失败。", INDEX_NAME);
            e.printStackTrace();
        }
    }

    @GetMapping(value = "get-by-id/{id}")
    public String getIndex(@PathVariable("id") String id) {
        GetRequest getRequest = new GetRequest(INDEX_NAME, id);
        try {
            if (!restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT)) {
                return null;
            }
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

            if (getResponse.isExists()) {

                long version = getResponse.getVersion();
                String source = getResponse.getSourceAsString();
                Map<String, Object> sourceAsString = getResponse.getSourceAsMap();
                byte[] sourceBytes = getResponse.getSourceAsBytes();

                log.info("version:[{}]", String.valueOf(version));
                log.info("source:[{}]", source);
                log.info("sourceAsString:[{}]", new ObjectMapper().writeValueAsString(sourceAsString));
                log.info("sourceAsBytes:[{}]", new ObjectMapper().writeValueAsString(sourceBytes));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("获取数据成功：[{}]", objectMapper.writeValueAsString(getResponse));
            StringBuilder sb = new StringBuilder();
            sb.append(objectMapper.writeValueAsString(getResponse));
            return sb.toString();
        } catch (IOException e) {
            log.error("获取index:[{}]，id：[{}]数据失败", INDEX_NAME, id);
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "exists-id/{id}")
    public Boolean existsId(@PathVariable("id") String id) {
        GetRequest getRequest = new GetRequest(INDEX_NAME, id);
        try {
            return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return false;
        }
    }

    @DeleteMapping(value = "delete-by-id/{id}")
    public Boolean deleteById(@PathVariable("id") String id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME, id);

        try {
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("数据[id:{}]删除成功。", id);
        } catch (IOException e) {
            log.error("数据[id:{}]删除失败。", id);
            e.printStackTrace();
        }
        return true;
    }

    @PutMapping(value = "update-by-id/{id}")
    public Boolean updateById(@PathVariable String id,
                              @RequestBody EsTest testBean) {

        try {
            UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, id)
                    .doc(BeanUtils.describe(testBean));

            restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return true;
    }

    @GetMapping(value = "term-vector-api/{id}/{fieldName}")
    public String termVectorApi(@PathVariable("id") String id,
                                @PathVariable("fieldName") String fieldName) {

        TermVectorsRequest request = new TermVectorsRequest(INDEX_NAME, id);
        request.setFields(fieldName);
        request.setFieldStatistics(false);
        request.setTermStatistics(true);
        request.setPositions(false);
        request.setOffsets(false);
        request.setPayloads(false);

        Map<String, Integer> filterSettings = new HashMap<>();
        filterSettings.put("max_num_terms", 3);
        filterSettings.put("min_term_freq", 1);
        filterSettings.put("max_term_freq", 10);
        filterSettings.put("min_doc_freq", 1);
        filterSettings.put("max_doc_freq", 100);
        filterSettings.put("min_word_length", 1);
        filterSettings.put("max_word_length", 10);
        request.setFilterSettings(filterSettings);

        Map<String, String> perFieldAnalyzer = new HashMap<>();
        perFieldAnalyzer.put(fieldName, "keyword");
        request.setPerFieldAnalyzer(perFieldAnalyzer);

        request.setRealtime(false);
        request.setRouting("routing");

        try {
            TermVectorsResponse response =
                    restHighLevelClient.termvectors(request, RequestOptions.DEFAULT);
            String res = new ObjectMapper().writeValueAsString(response);
            log.info("term vector result:{}", res);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "bulk-operate/{type}")
    public Boolean bulk(@PathVariable String type,
                        @RequestBody List<EsTest> testBeans){
        BulkRequest request = new BulkRequest();

        try {
            if ("add".equals(type)) {
                for (EsTest testBean : testBeans) {

                    request.add(
                            new IndexRequest(INDEX_NAME).id(testBean.getId())
                                    .source(BeanUtils.describe(testBean))
                    );


                }
                restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            } else if ("update".equals(type)) {
                for (EsTest testBean : testBeans) {
                    request.add(
                            new UpdateRequest(INDEX_NAME, testBean.getId())
                                    .doc(BeanUtils.describe(testBean))
                    );
                }
                restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

            } else if ("delete".equals(type)) {
                for (EsTest testBean : testBeans) {
                    request.add(
                            new DeleteRequest(INDEX_NAME, testBean.getId())
                    );
                }
                restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
            e.printStackTrace();
        }
        return true;

    }


    @PostMapping(value = "multi-get")
    public String multiGet(@RequestBody List<EsTest> testBeans){
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            List<EsTest> res = new ArrayList<>();
            MultiGetRequest request = new MultiGetRequest();
            String[] includes = Strings.EMPTY_ARRAY;
            String[] excludes = new String[] {"class"};
            FetchSourceContext fetchSourceContext =
                    new FetchSourceContext(true, includes, excludes);
            for (EsTest testBean : testBeans) {
                request.add(
                        new MultiGetRequest.Item(INDEX_NAME, testBean.getId())
                                .fetchSourceContext(fetchSourceContext)
                );

            }

            MultiGetResponse multiGetResponse = restHighLevelClient.mget(request, RequestOptions.DEFAULT);
            log.info(objectMapper.writeValueAsString(multiGetResponse.getResponses()));

            for (MultiGetItemResponse multiGetItemResponse : multiGetResponse.getResponses()) {
                EsTest tmp = objectMapper.readValue(
                        multiGetItemResponse.getResponse().getSourceAsString(),
                        EsTest.class
                );

                log.info(tmp.getPostDate().toString());
                res.add(
                        tmp
                );
            }

            return objectMapper.writeValueAsString(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @GetMapping(value = "test-sql")
    public String testSql() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<EsTest> aaa =  esTestService.getAll();
        try {
            return objectMapper.writeValueAsString(aaa);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
