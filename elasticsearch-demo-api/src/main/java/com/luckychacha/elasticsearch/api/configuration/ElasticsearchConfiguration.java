package com.luckychacha.elasticsearch.api.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfiguration {
    //ES地址
    @Value("${data.elasticsearch.host}")
    private String host;
    //ES端口
    @Value("${data.elasticsearch.port}")
    private int port;

    /**
     * @return 封装 RestClient
     */
    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
    }
}
