package com.luckychacha.elasticsearch.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Configuration
//@MapperScan(basePackages = {"com.luckychacha.elasticsearch.dao.mysql"}, sqlSessionFactoryRef = "sqlSessionFactory")
//public class MybatisConfiguration {
//
//    @Value("${mybatis.mapper-locations}")
//    private String mapperLocationPattern;
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource(){
//        return new com.alibaba.druid.pool.DruidDataSource();
//    }
//
//    @Bean(name="sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws  Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocationPattern));
//        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
//        sqlSessionFactoryBean.setTypeHandlersPackage("com.luckychacha.elasticsearch.dao.handler");
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.luckychacha.elasticsearch.model.entity");
//        return sqlSessionFactoryBean.getObject();
//    }

//}
